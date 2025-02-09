package com.astrapay.controller;

import com.astrapay.dto.NoteDto;
import com.astrapay.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public ResponseEntity<Map<String,Object>> get_note(@RequestParam(required=false) String id) {

    	Map<String, Object> response = new HashMap<>();
    	
        try {
        	
        	if (id != null) {
        		List<NoteDto> filteredList;
                filteredList = noteCollection.stream()
                  .filter(note -> id.contains(note.id))
                  .collect(Collectors.toList());
                response.put("data", filteredList);
        	}else {
        		response.put("data", noteCollection);
        	}
            return ResponseEntity.ok(response);
        } catch (Exception e) {
    		response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @PostMapping("/upsert-note")
    @ApiOperation(value = "Update or Add New Note")
    public ResponseEntity<Map<String, Object>> upsert_note(@RequestBody Map<String,Object> body) {

    	Map<String, Object> response = new HashMap<>();
    	
    	try {
    		//validation
    		if (!body.containsKey("title")) { //missing param: title
    			response.put("error", "Missing 'title' parameter");
    			return ResponseEntity.badRequest().body(response);
    		}else if (body.get("title").toString() == ""){ //param title is empty
    			response.put("error", "'title' parameter can not be empty");
    			return ResponseEntity.badRequest().body(response); 
    		}else if (!body.containsKey("notes")) {	//missing param: notes
    			response.put("error", "Missing 'notes' parameter");
    			return ResponseEntity.badRequest().body(response);
    		}else if (body.containsKey("id") && body.get("id").toString() != ""){ //has id param, update
    			int index = IntStream.range(0, noteCollection.size())
    					.filter(i -> noteCollection.get(i).id.equals(body.get("id").toString()))
    					.findFirst()
    					.orElse(-1);
    			if (index == -1) {
    				response.put("error", "Notes not found");
        			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    			}else {
    				noteCollection.get(index).updateNote(body.get("title").toString(), body.get("notes").toString());
    				response.put("data", noteCollection);
            		return ResponseEntity.ok(response);
    			}
    		}else {
    			NoteDto noteSubmitted = new NoteDto();
            	noteSubmitted.addNote(body.get("title").toString(), body.get("notes").toString());
            	noteCollection.add(noteSubmitted);
        		response.put("data", noteCollection);
        		return ResponseEntity.ok(response);
    		}
        } catch (Exception e) {
    		response.put("error", e.getMessage());
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @DeleteMapping("/delete-note")
    @ApiOperation(value = "DeleteNote")
    public ResponseEntity<Map<String,Object>> delete_note(@RequestParam String id){
    	
    	Map<String, Object> response = new HashMap<>();
    	
    	try {
        	boolean deletedNote;
            deletedNote = noteCollection.removeIf(note -> id.contains(note.id));
            if(deletedNote) {
            	response.put("message", "Note " + id + " has been deleted");
            }else {
            	response.put("error", "Note " + id + " has not been deleted");
            }
            return ResponseEntity.ok(response);
    	} catch (Exception e) {
    		response.put("error", e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    	}
    }
    
    

}