package com.wojo.authservice.entity

import javax.persistence.*

@Entity
data class UserRole(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Column(nullable = false)
        val userCode: Long = 0,

        @ManyToOne
        val role: Role = Role()

) {

    private constructor(builder: Builder) : this(
            id = builder.id,
            userCode = builder.userCode,
            role = builder.role
    )

    companion object {
        inline fun build(block: Permission.Builder.() -> Unit) = Permission.Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0
        var userCode: Long = 0
        var role: Role = Role()

        fun build() = UserRole(this)
    }
}