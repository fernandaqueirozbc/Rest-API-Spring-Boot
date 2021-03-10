package br.com.bandtec.Continuada2021.controller

import br.com.bandtec.Continuada2021.domain.Boletim
import org.springframework.web.bind.annotation.*
import javax.persistence.criteria.CriteriaBuilder


@RestController
@RequestMapping("/boletins")
class BoletimController{

    val boletimAlunos = mutableListOf<Boletim>();

    @PostMapping
    fun addAluno(@RequestBody bl: Boletim){
     boletimAlunos.add(bl)
    }

    @GetMapping
    @ResponseBody
    fun ExibirTudo(){
        return println(boletimAlunos)
    }

    @GetMapping("/{id}")
    fun ExibirUmAluno(@PathVariable id: Int){
        return println(boletimAlunos.get(id))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) : String {
        if(boletimAlunos.size -1>= id) {
            boletimAlunos.removeAt(id - 1)
            return "Boletim excluído com sucesso"
        }else{
            return "Boletim não encontrado!"
        }
    }
}