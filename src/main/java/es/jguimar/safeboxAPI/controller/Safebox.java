package es.jguimar.safeboxAPI.controller;

import es.jguimar.safeboxAPI.model.ContentDTO;
import es.jguimar.safeboxAPI.model.SafeboxDTO;
import es.jguimar.safeboxAPI.model.LoginSafeboxDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

public interface Safebox {

    @Operation(summary = "Retrieves the content of a safebox", description = "Retrieves the currently stored contents in the safebox identified by the given ID and with the given opening token.", security = {
        @SecurityRequirement(name = "bearerToken")    }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Safebox contents correctly retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContentDTO.class))),
        
        @ApiResponse(responseCode = "401", description = "Specified token does not match"),
        
        @ApiResponse(responseCode = "404", description = "Requested safebox does not exist"),
        
        @ApiResponse(responseCode = "422", description = "Malformed expected data"),
        
        @ApiResponse(responseCode = "423", description = "Requested safebox is locked"),
        
        @ApiResponse(responseCode = "500", description = "Unexpected API error") })
    @RequestMapping(value = "/safebox/{id}/items",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ContentDTO safeboxIdItemsGet(@Parameter(in = ParameterIn.PATH, description = "Safebox Id", required=true, schema=@Schema()) @PathVariable("id") String id);


    @Operation(summary = "Add an items to a Safebox", description = "Inserts new contents in the safebox identified by the given ID and with the given opening token. ", security = {
        @SecurityRequirement(name = "bearerToken")    }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Content correctly added to the safebox"),
        
        @ApiResponse(responseCode = "401", description = "Specified token does not match"),
        
        @ApiResponse(responseCode = "404", description = "Requested safebox does not exist"),
        
        @ApiResponse(responseCode = "422", description = "Malformed expected data"),
        
        @ApiResponse(responseCode = "423", description = "Requested safebox is locked"),
        
        @ApiResponse(responseCode = "500", description = "Unexpected API error") })
    @RequestMapping(value = "/safebox/{id}/items",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    void safeboxIdItemsPut(@Parameter(in = ParameterIn.PATH, description = "Safebox id", required=true, schema=@Schema()) @PathVariable("id") String id, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ContentDTO body);


    @Operation(summary = "Open a safebox", description = "Opens the safebox identified by the given ID and with the specified user & password. ", security = {
        @SecurityRequirement(name = "basicPassword")    }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Safebox correctly opened", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
        
        @ApiResponse(responseCode = "404", description = "Requested safebox does not exist"),
        
        @ApiResponse(responseCode = "422", description = "Malformed expected data"),
        
        @ApiResponse(responseCode = "423", description = "Requested safebox is locked"),
        
        @ApiResponse(responseCode = "500", description = "Unexpected API error") })
    @RequestMapping(value = "/safebox/{id}/open",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    Map safeboxIdOpenGet(@Parameter(in = ParameterIn.PATH, description = "Safebox id", required=true, schema=@Schema()) @PathVariable("id") String id);


    @Operation(summary = "Creates a new safebox", description = "Creates a new safebox based on a non-empty name and a password. ", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Safebox correctly created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SafeboxDTO.class))),
        
        @ApiResponse(responseCode = "409", description = "Safebox already exists"),
        
        @ApiResponse(responseCode = "422", description = "Malformed expected data"),
        
        @ApiResponse(responseCode = "500", description = "Unexpected API error") })
    @RequestMapping(value = "/safebox",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    SafeboxDTO safeboxPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody LoginSafeboxDTO body);

}

