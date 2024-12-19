package com.example.musicapp.controller;

import com.example.musicapp.model.Song;
import com.example.musicapp.model.SongForm;
import com.example.musicapp.service.ISongService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/songs")
public class SongController {
    @Autowired
    private ISongService songService;

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping
    public String showSongList(ModelMap model) {
        model.addAttribute("songs", songService.findAll());
        return "index";
    }

    @GetMapping("/create")
    public String createSong(ModelMap model) {
        model.addAttribute("songForm", new SongForm());
        return "create";
    }

    @PostMapping("/save")
    public String saveSong(SongForm songForm) {
        Song song = uploadFile(songForm);;
        songService.save(song);
        return "redirect:/songs";
    }

    @GetMapping("/{id}/view")
    public String viewSong(@PathVariable int id, ModelMap model) {
        model.addAttribute("song", songService.findById(id));
        return "view";
    }

    @GetMapping("/{id}/edit")
    public String editSong(@PathVariable int id, ModelMap model) {
        Song song = songService.findById(id);
        SongForm songForm = new SongForm();
        songForm.setId(id);
        songForm.setTitle(song.getTitle());
        songForm.setSinger(song.getSinger());
        songForm.setCategory(song.getCategory());
        model.addAttribute("fileName", song.getFile());
        model.addAttribute("songForm", songForm);
        return "update";
    }

    @PostMapping("/update")
    public String updateSong(SongForm songForm, RedirectAttributes redirectAttributes) {
        Song song = uploadFile(songForm);;
        songService.save(song);
        redirectAttributes.addFlashAttribute("success", "Song updated successfully");
        return "redirect:/songs";
    }

    private Song uploadFile(SongForm songForm) {
        MultipartFile multipartFile = songForm.getFile();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + fileName)) ;
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        return new Song(songForm.getId(), songForm.getTitle(), songForm.getSinger(),
                songForm.getCategory(), fileName);
    }

    @GetMapping("/{id}/delete")
    public String deleteSong(@PathVariable int id, RedirectAttributes redirectAttributes) {
        songService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Song deleted successfully");
        return "redirect:/songs";
    }
}
