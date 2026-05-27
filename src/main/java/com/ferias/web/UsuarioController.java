package com.ferias.web;

import com.ferias.dto.UsuarioRequest;
import com.ferias.dto.UsuarioResponse;
import com.ferias.service.UsuarioCrudService;
import com.ferias.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioCrudService service;

    public UsuarioController(UsuarioCrudService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public UsuarioResponse criar(@RequestBody UsuarioRequest request, HttpServletRequest http) {
        return service.criar(request, AuthUtil.usuarioId(http));
    }

    @PutMapping("/{id}")
    public UsuarioResponse atualizar(
            @PathVariable Integer id,
            @RequestBody UsuarioRequest request,
            HttpServletRequest http) {
        return service.atualizar(id, request, AuthUtil.usuarioId(http));
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        service.excluir(id);
    }
}
