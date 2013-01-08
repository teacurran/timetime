package com.sun.jersey.wadl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.sun.jersey.server.wadl.ApplicationDescription;
import com.sun.research.ws.wadl.Resources;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.jersey.server.impl.modelapi.annotation.IntrospectionModeller;
import com.sun.jersey.server.wadl.WadlBuilder;
import com.sun.jersey.server.wadl.WadlGenerator;
import com.sun.jersey.server.wadl.WadlGeneratorImpl;
import com.sun.jersey.wadl.resourcedoc.ResourceDoclet;
import com.sun.research.ws.wadl.Application;

/**
 * This ant task generates a wadl file, using an option resourcedoc file created
 * by the {@link ResourceDoclet} and an applicationdoc file.<br />
 * Created on: Jun 18, 2008<br />
 *
 * @author Andrew Ochsner
 * @version $Id$
 *
 */
public class GenerateWadlTask extends Task {

	public class Package {
		private String name;

		public Package() {

		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public class Packages {
		public Packages() {
			packages = new ArrayList<Package>();
		}

		public Package createPackage() {
			Package _package = new Package();
			packages.add(_package);
			return _package;
		}

		public List<Package> getPackages() {
			return packages;
		}

		private List<Package> packages;
	}

	public class WadlGeneratorInfoParam {
		private String name;
		private String value;

		public WadlGeneratorInfoParam() {

		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	public class WadlGeneratorInfo {
		private String name;
		private List<WadlGeneratorInfoParam> params;

		public WadlGeneratorInfo() {
			params = new ArrayList<WadlGeneratorInfoParam>();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<WadlGeneratorInfoParam> getParams() {
			return params;
		}

		public WadlGeneratorInfoParam createParam() {
			WadlGeneratorInfoParam param = new WadlGeneratorInfoParam();
			params.add(param);
			return param;
		}
	}

	public class WadlGenerators {
		private List<WadlGeneratorInfo> wadlGeneratorDescriptions;

		public WadlGenerators() {
			wadlGeneratorDescriptions = new ArrayList<WadlGeneratorInfo>();
		}

		public WadlGeneratorInfo createWadlGeneratorDescription() {
			WadlGeneratorInfo info = new WadlGeneratorInfo();
			wadlGeneratorDescriptions.add(info);
			return info;
		}

		public List<WadlGeneratorInfo> getWadlGeneratorDescriptions() {
			return wadlGeneratorDescriptions;
		}
	}

	/**
	 * Location of the wadl file to create.
	 *
	 * @parameter property="wadlFile"
	 *            expression="${project.build.directory}/application.wadl"
	 * @required
	 */
	private File _wadlFile;

	/**
	 * Specifies, if the generated wadl file shall contain formatted xml or not.
	 * The default value is <code>true</code>.
	 *
	 * @parameter property="formatWadlFile"
	 */
	private boolean _formatWadlFile = true;

	/**
	 * The base-uri to use.
	 *
	 * @parameter property="baseUri"
	 * @required
	 */
	private String _baseUri;

	/**
	 * A list of packages that is searched for resource classes
	 *
	 * @paramter property="packages"
	 * @required
	 */
	private Packages _packages;

	/**
	 * A list of wadl generators to run
	 *
	 * @parameter property="wadlGenerators"
	 * @required
	 */
	private WadlGenerators _wadlGenerators;

	/**
	 * @parameter property="classpathref"
	 * @required
	 */
	private Path _classpath;

	@Override
	public void execute() throws BuildException {
		if (_packages == null || _packages.getPackages().size() == 0) {
			throw new BuildException(
					"A fileset of resources is required but not defined.");
		}
		if (_wadlFile == null) {
			throw new BuildException(
					"The wadlFile attribute is required but not defined.");
		}

		if (_baseUri == null || _baseUri.length() == 0) {
			throw new BuildException(
					"The baseUri attribute is required but not defined.");
		}
		AntClassLoader classLoader = null;
		try {

			classLoader = getProject().createClassLoader(_classpath);
			classLoader.setParent(this.getClass().getClassLoader());
			classLoader.setThreadContextLoader();
			com.sun.jersey.server.wadl.WadlGenerator wadlGenerator = new WadlGeneratorImpl();
			if (_wadlGenerators != null) {
				for (WadlGeneratorInfo wadlGeneratorInfo : _wadlGenerators
						.getWadlGeneratorDescriptions()) {
					wadlGenerator = loadWadlGenerator(wadlGeneratorInfo,
							wadlGenerator);
				}
			}
			wadlGenerator.init();

			final ApplicationDescription appDescription = createApplication(wadlGenerator);
			final Application app = appDescription.getApplication();
			List<Resources> resources = app.getResources();

			for (Resources resource : resources) {
				resource.setBase(_baseUri);
			}

			final JAXBContext c = JAXBContext.newInstance(wadlGenerator
					.getRequiredJaxbContextPath(), Thread.currentThread()
					.getContextClassLoader());
			final Marshaller m = c.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, _formatWadlFile);
			final OutputStream out = new BufferedOutputStream(
					new FileOutputStream(_wadlFile));

			final XMLSerializer serializer = getXMLSerializer(out);

			m.marshal(app, serializer);
			out.close();

			log("Wrote " + _wadlFile);

		} catch (Exception e) {
			if (classLoader != null) {
				classLoader.resetThreadContextLoader();
				classLoader.cleanup();
			}

			throw new BuildException("Could not write wadl file", e);
		}
	}

	private XMLSerializer getXMLSerializer(OutputStream out)
			throws FileNotFoundException {
		// configure an OutputFormat to handle CDATA
		OutputFormat of = new OutputFormat();

		// specify which of your elements you want to be handled as CDATA.
		// The use of the '^' between the namespaceURI and the localname
		// seems to be an implementation detail of the xerces code.
		// When processing xml that doesn't use namespaces, simply omit the
		// namespace prefix as shown in the third CDataElement below.
		of.setCDataElements(new String[] {
				"http://research.sun.com/wadl/2006/10^doc", // <ns1:foo>
				"ns2^doc", // <ns2:bar>
				"^doc"
		/*
		 * , "ns2:doc", "doc"
		 */}); // <baz>

		// set any other options you'd like
		of.setPreserveSpace(true);
		of.setIndenting(true);
		// of.setLineWidth( 120 );
		// of.setNonEscapingElements( new String[] {
		// "http://www.w3.org/1999/xhtml^br", "http://www.w3.org/1999/xhtml^br"
		// } );

		// create the serializer
		XMLSerializer serializer = new XMLSerializer(of);

		serializer.setOutputByteStream(out);

		return serializer;
	}

	private WadlGenerator loadWadlGenerator(
			WadlGeneratorInfo wadlGeneratorInfo,
			com.sun.jersey.server.wadl.WadlGenerator wadlGeneratorDelegate)
			throws Exception {
		log("Loading wadlGeneratorInfo " + wadlGeneratorInfo.getName());
		final Class<?> clazz = Class.forName(wadlGeneratorInfo.getName(), true,
				Thread.currentThread().getContextClassLoader());
		final WadlGenerator generator = clazz.asSubclass(WadlGenerator.class)
				.newInstance();
		generator.setWadlGeneratorDelegate(wadlGeneratorDelegate);
		if (!wadlGeneratorInfo.getParams().isEmpty()) {
			for (WadlGeneratorInfoParam param : wadlGeneratorInfo.getParams()) {
				setProperty(generator, param.getName(), param.getValue());
			}
		}
		return generator;
	}

	private void setProperty(final Object object, final String propertyName,
			final Object propertyValue) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			InstantiationException {
		final String methodName = "set"
				+ propertyName.substring(0, 1).toUpperCase()
				+ propertyName.substring(1);
		final Method method = getMethodByName(methodName, object.getClass());
		if (method.getParameterTypes().length != 1) {
			throw new RuntimeException(
					"Method "
							+ methodName
							+ " is no setter, it does not expect exactly one parameter, but "
							+ method.getParameterTypes().length);
		}
		final Class<?> paramClazz = method.getParameterTypes()[0];
		if (paramClazz == propertyValue.getClass()) {
			method.invoke(object, propertyValue);
		} else {
			/*
			 * does the param class provide a constructor for string?
			 */
			final Constructor<?> paramTypeConstructor = paramClazz
					.getConstructor(propertyValue.getClass());
			if (paramTypeConstructor != null) {
				final Object typedPropertyValue = paramTypeConstructor
						.newInstance(propertyValue);
				method.invoke(object, typedPropertyValue);
			} else {
				throw new RuntimeException(
						"The property '"
								+ propertyName
								+ "' could not be set"
								+ " because the expected parameter is neither of type "
								+ propertyValue.getClass()
								+ " nor of any type that provides a constructor expecting a "
								+ propertyValue.getClass() + "."
								+ " The expected parameter is of type "
								+ paramClazz.getName());
			}
		}
	}

	private Method getMethodByName(final String methodName, final Class<?> clazz) {
		for (Method method : clazz.getMethods()) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		throw new RuntimeException("Method '" + methodName
				+ "' not found for class " + clazz.getName());
	}

	private ApplicationDescription createApplication(WadlGenerator wadlGenerator) {
		final Map<String, Object> map = new HashMap<String, Object>();
		List<String> packageNames = new ArrayList<String>();
		for (Package p : _packages.getPackages()) {
			packageNames.add(p.getName());
		}
		map.put(PackagesResourceConfig.PROPERTY_PACKAGES, packageNames
				.toArray(new String[] {}));
		final ResourceConfig rc = new PackagesResourceConfig(map);
		final Set<AbstractResource> s = new HashSet<AbstractResource>();
		for (Class<?> c : rc.getRootResourceClasses()) {
			log("Adding class " + c.getName());
			s.add(IntrospectionModeller.createResource(c));
		}
		return new WadlBuilder(wadlGenerator).generate(s);
	}

	public void setWadlFile(File file) {
		_wadlFile = file;
	}

	public void setFormatWadlFile(boolean wadlFile) {
		_formatWadlFile = wadlFile;
	}

	public void setBaseUri(String uri) {
		_baseUri = uri;
	}

	public void setClasspath(Path path) {
		if (_classpath == null) {
			_classpath = path;
		} else {
			_classpath.append(path);
		}
	}

	public Path createClasspath() {
		if (_classpath == null) {
			_classpath = new Path(getProject());
		}
		return _classpath.createPath();
	}

	public void setClasspathRef(Reference r) {
		createClasspath().setRefid(r);
	}

	public Packages createPackages() {
		if (_packages == null) {
			_packages = new Packages();
		}
		return _packages;
	}

	public WadlGenerators createWadlGenerators() {
		if (_wadlGenerators == null) {
			_wadlGenerators = new WadlGenerators();
		}
		return _wadlGenerators;
	}
}