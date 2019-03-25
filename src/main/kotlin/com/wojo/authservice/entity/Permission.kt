package com.wojo.authservice.entity

import javax.persistence.*

@Entity
data class Permission(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Column(nullable = false)
        val name: String = "",

        @OneToMany(mappedBy = "role")
        val roles: List<RolePermission> = emptyList()

) {

    private constructor(builder: Builder) : this(
            id = builder.id,
            name = builder.name,
            roles = builder.roles
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0
        var name: String = ""
        var roles: List<RolePermission> = emptyList()

        fun build() = Permission(this)
    }
}