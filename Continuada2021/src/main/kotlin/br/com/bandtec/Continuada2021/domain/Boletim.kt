package br.com.bandtec.Continuada2021.domain

import javax.persistence.GeneratedValue
import javax.persistence.Id

data class Boletim(

        val RA: Int,
        val nomeAluno: Int,
        val nota1: Double,
        val nota2: Double,
        var mediaAluno: Double
)
{

    fun media() {mediaAluno = (nota1 + nota2) / 2} ;

    override fun toString(): String {
        return "{RA:$RA," +
                "Nome Aluno:$nomeAluno," +
                "Nota 1:$nota1, " +
                "Nota 2:$nota2"+
                "Media:$mediaAluno}"
    }
}
