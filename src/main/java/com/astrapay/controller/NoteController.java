package com.astrapay.controller;

import com.astrapay.dto.NoteDto;
import com.astrapay.exception.ExampleException;
import com.astrapay.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "NoteController")
@Slf4j
public class NoteController {
    private final NoteService noteService;
    private List<NoteDto> noteCollection;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
        this.noteCollection = new ArrayList<NoteDto>();
    }

    @GetMapping("/get-note")
    @ApiOperation(value = "Get Note")
    public ResponseEntity<Map<String,Object>> get_note() {

        try {
        	Map<String, Object> response = new HashMap<>();
    		response.put("data", noteCollection);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
        	Map<String, Object> response = new HashMap<>();
    		response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @PostMapping("/add-note")
    @ApiOperation(value = "Add Note")
    public ResponseEntity<Map<String, Object>> add_note(@RequestBody Map<String,Object> body) {

        try {
        	NoteDto noteSubmitted = new NoteDto();
        	noteSubmitted.addNote(body.get("title").toString(), body.get("note").toString());
        	noteCollection.add(noteSubmitted);
        	Map<String, Object> response = new HashMap<>();
    		response.put("data", noteCollection);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
        	Map<String, Object> response = new HashMap<>();
    		response.put("error", e.getMessage());
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @DeleteMapping("/delete-note")
    @ApiOperation(value = "DeleteNote")
    public ResponseEntity<Map<String,String>> delete_note(@RequestParam String id){
    	try {
    		Map<String, String> response = new HashMap<>();
    		response.put("error", id.toString());
    		return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		Map<String, String> response = new HashMap<>();
    		response.put("error", e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    	}
    }
    
    

}