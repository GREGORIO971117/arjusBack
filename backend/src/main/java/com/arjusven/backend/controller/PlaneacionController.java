package com.arjusven.backend.controller;

import com.arjusven.backend.dto.PatchPlaneacionDTO;
import com.arjusven.backend.dto.PlaneacionDTO;
import com.arjusven.backend.service.PlaneacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planeacion")
public class PlaneacionController {

    private PlaneacionService planeacionService;

    @Autowired
    public PlaneacionController(PlaneacionService planeacionService) {
        this.planeacionService = planeacionService;
    }

    @GetMapping
    public ResponseEntity<List<PlaneacionDTO>> getPlaneacionData() {
        try {
            List<PlaneacionDTO> data = planeacionService.getPlaneacionOperativa();
            if (data.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{idTicket}")
    public ResponseEntity<?> actualizarPlaneacion(
            @PathVariable Long idTicket,
            @RequestBody PatchPlaneacionDTO dto) {

        try {
            PlaneacionDTO actualizado = planeacionService.patchPlaneacion(idTicket, dto);
            return ResponseEntity.ok(actualizado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor: " + e.getMessage());
        }
    }
}
