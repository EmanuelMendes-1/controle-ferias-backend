package com.ferias.web;

import com.ferias.dto.PerfilRequest;
import com.ferias.dto.PerfilResponse;
import com.ferias.service.PerfilService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfis")
@CrossOrigin(origins = "*")
public class PerfilController {

    private final PerfilService service;

    public PerfilController(PerfilService service) {
        this.service = service;
    }

    @GetMapping
    public List<PerfilResponse> listar() {
        return service.listar();
    }

    @PostMapping
    public PerfilResponse criar(@RequestBody PerfilRequest request) {
        return service.criar(request);
    }

    @PutMapping("/{id}")
    public PerfilResponse atualizar(@PathVariable Integer id, @RequestBody PerfilRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        service.excluir(id);
    }
}

