package com.danilo.project.taskmanager.taskmanager.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class HelloController {

    val contador: AtomicLong = AtomicLong()

    @RequestMapping("/saudacao")
    fun saudacao(@RequestParam(value="nome", defaultValue = "World") nome: String): Saudacao {
        return Saudacao(contador.incrementAndGet(),
            "Task Manager Api - Bem vindo(a) $nome")
    }
}

class Saudacao(val id: Long, val content: String)
