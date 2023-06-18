package com.root.project.user.resource;

import com.root.project.user.User;
import com.root.project.user.dto.UserDto;
import com.root.project.user.model.request.UserRm;
import com.root.project.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("users")
public class UserResource {
    @Autowired
    UserService userService;
    @Operation(summary = "Create New User", description = "Create User", tags = "POST")
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    content =  {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Bad request",
                    content = {@Content}
            )
    })
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){

        return ResponseEntity.ok(this.userService.createUser(userDto));
    }

    @Operation(summary="Update User", description = "Update user", tags="PUT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(mediaType = "application/json" ,schema = @Schema(implementation = User.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Bad Request", content = {@Content})
    })
    @PutMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody UserDto user){
        return ResponseEntity.ok(this.userService.updateUser(user));
    }
}
