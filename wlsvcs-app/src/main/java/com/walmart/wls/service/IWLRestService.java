package com.walmart.wls.service;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.walmart.services.pymt.PaymentsRequest;
import com.walmart.services.pymt.PaymentsResponse;
import com.walmart.wls.dto.Pihash;
/**
 * Carrier Rate Code Management RESTful APIs.
 * 
 * These APIs are consumed by OMS Portal and Order Release Engines.
 * @author gtulla
 *
 */
@Path("/wls-srvs")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface IWLRestService {
	@POST
	@Path("/configs")
	public String helloStrati(@QueryParam("name") String request);
	
	@GET
	@Path("/payments/paymentOption")
	public Pihash determinePaymentOption(@QueryParam("query") String query);
	
	@POST
	@Path("/payments/lineOfCredit")
	public Pihash lineOfCredit(@QueryParam("baseMembershipId") String baseMembershipId);
	@POST
	@Path("/payments/view")
	public PaymentsResponse view(PaymentsRequest request);
}
