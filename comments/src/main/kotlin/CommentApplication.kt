package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories("org.example.repository")
class CommentApplication
fun main(args: Array<String>) {
    runApplication<CommentApplication>(*args)
}