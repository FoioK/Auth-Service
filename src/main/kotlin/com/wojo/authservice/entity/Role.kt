package com.wojo.authservice.entity

import javax.persistence.*

@Entity
data class Role(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Column(nullable = false)
        val name: String = "",

        @OneToMany(mappedBy = "role")
        val users: List<UserRole> = emptyList(),

        @OneToMany(mappedBy = "permission")
        val permissions: List<RolePermission> = emptyList()

) {

    private constructor(builder: Builder) : this(
            id = builder.id,
            name = builder.name,
            users = builder.users,
            permissions = builder.permissions
    )

    companion object {
        inline fun build(block: Permission.Builder.() -> Unit) = Permission.Builder().apply(block).build()
    }

    class Builder {
        var id: Long = 0
        var name: String = ""
        var users: List<UserRole> = emptyList()
        var permissions: List<RolePermission> = emptyList()

        fun build() = Role(this)
    }
}