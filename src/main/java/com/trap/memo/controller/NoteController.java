package com.trap.memo.controller;

import com.trap.memo.model.Note;
import com.trap.memo.model.User;
import com.trap.memo.service.NoteService;
import com.trap.memo.service.SSLService;
import com.trap.memo.service.SecurityService;
import com.trap.memo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class NoteController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    NoteService noteService;
    @Autowired
    UserService userService;


    @GetMapping(value = "/create")
    public String createNote(Model model) {
        model.addAttribute("noteForm", new Note());
        return "create";
    }
    @PostMapping(value = "/create")
    public String createNote(@Valid @ModelAttribute("noteForm") Note noteForm, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        noteForm.setCreateDate(new Date());
        try {
            byte[]  bytes = SSLService.gen(noteForm.getName());
            noteForm.setPhoto(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String username = securityService.findLoggedInUsername();
        User user = userService.findByUsername(username);
        noteForm.setUser(user);
        noteService.save(noteForm);
        if (bindingResult.hasErrors()) {
            return "create";
        }

        return "redirect:/notes";
    }
    @GetMapping(value = {"/", "/notes"})
    public String welcome(Model model) {
        String username = securityService.findLoggedInUsername();
        List<Note> notes = noteService.getAllByUser(username);
        model.addAttribute("notes",notes);
        return "notes";
    }

    @GetMapping(value = "/search")
    public String search(Model model, @RequestParam String name){
        String username = securityService.findLoggedInUsername();
        List<Note> notes = noteService.getAllByUser(username);
        List<Note> foundNotes = new ArrayList<>();
        for (Note note : notes){
            if(note.getName().contains(name))
                foundNotes.add(note);
        }
        model.addAttribute("notes",foundNotes);
        return "notes";
    }
    @GetMapping(value = "notes/{id}")
    public String showNote(Model model,@PathVariable Long id){
        Note note = noteService.getOne(id);
        User user = note.getUser();
        if(securityService.findLoggedInUsername().equals(user.getUsername())){
        model.addAttribute("note",note);
        return "note";
        } else
            return "error";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String deleteNote(Model model,@PathVariable Long id){
        Note note = noteService.getOne(id);
        User user = note.getUser();
        if(securityService.findLoggedInUsername().equals(user.getUsername())) {
            noteService.delete(id);
            return "redirect:notes";
        } else return "error";
    }

    @RequestMapping(value = "/photo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] downloadPhoto(@PathVariable("id") Long id) {
        Note note = noteService.getOne(id);

        //        if (note.getPhoto() != null) {
//            logger.info("Downloading photo for id: {} with size: {}", note.getId(),
//                    singer.getPhoto().length);
//        }

        return note.getPhoto();
    }
}
