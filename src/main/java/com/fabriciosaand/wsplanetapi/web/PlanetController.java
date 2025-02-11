package com.fabriciosaand.wsplanetapi.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fabriciosaand.wsplanetapi.domain.Planet;
import com.fabriciosaand.wsplanetapi.domain.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planet){
        Planet planetCreated = planetService.create(planet);
        return ResponseEntity.status(HttpStatus.CONTINUE).body(planetCreated); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> get(@PathVariable Long id){
        return planetService.get(id).map(planet -> ResponseEntity.ok(planet))
            .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Planet> get(@PathVariable String name){
        return planetService.getByName(name).map(planet -> ResponseEntity.ok(planet))
            .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Planet>> list(@RequestParam(required = false) String terrain, 
        @RequestParam(required = false) String climate){
        List<Planet> planets = planetService.list(terrain, climate);
        return ResponseEntity.ok(planets);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Planet> delete(@PathVariable Long id){
        planetService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
