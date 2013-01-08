package com.approachingpi.timetime.api.v1.resources;

import javax.inject.Named;
import javax.ws.rs.Path;

import com.approachingpi.timetime.services.BaseService;
import org.jboss.seam.validation.AutoValidating;

/**
 * Date: 1/7/13
 *
 * @author T. Curran
 */
@Path("/account")
@AutoValidating
@Named
public class AccountResource extends BaseService {
}
