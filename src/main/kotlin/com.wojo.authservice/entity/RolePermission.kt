package com.wojo.authservice.entity

import javax.persistence.*

@Entity
data class RolePermission(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @ManyToOne
        val role: Role = Role(),

        @ManyToOne
        val permission: Permission = Permission()

) {

    private constructor(builder: Builder) : this(
            id = builder.id,
            role = builder.role,
            permission = builder.permission
    )

    companion object {
        inline fun build(block: Permission.Builder.() -> Unit) = Permission.Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0
        var role: Role = Role()
        var permission: Permission = Permission()

        fun build() = RolePermission(this)
    }
}