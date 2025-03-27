package oportunia.maps.frontend.taskapp.data.datasource.userrole

import android.util.Log
import oportunia.maps.frontend.taskapp.domain.model.User
import oportunia.maps.frontend.taskapp.domain.model.Role
import oportunia.maps.frontend.taskapp.domain.model.UserRole
import oportunia.maps.frontend.taskapp.data.datasource.model.enumClasses.TypeUser

/**
 * This class simulates the interaction with UserRole data.
 */
class UserRoleProvider {
    companion object {
        private val userRoleList = mutableListOf(
            UserRole(
                user = User(
                    id = 1,
                    email = "student@est.una.ac.cr",
                    password = "123"
                ),
                role = Role(
                    id = 1,
                    name = TypeUser.STU
                )
            ),
            UserRole(
                user = User(
                    id = 2,
                    email = "company@example.com",
                    password = "123"
                ),
                role = Role(
                    id = 2,
                    name = TypeUser.COM
                )
            )
        )

        fun verifyUserCredentials(email: String, password: String): Boolean {
            return userRoleList.any { it.user.email == email && it.user.password == password }
        }

        fun getUserRoleByEmail(email: String): TypeUser? {
            Log.d("UserRoleProvider", "Fetching role for email: $email")
            val userRole = userRoleList.find { it.user.email == email }
            if (userRole == null) {
                Log.d("UserRoleProvider", "User not found for email: $email")
            } else {
                Log.d("UserRoleProvider", "User role found: ${userRole.role.name}")
            }
            return userRole?.role?.name
        }

        fun findUserRoleByUserId(userId: Long): UserRole? {
            return userRoleList.find { it.user.id == userId }
        }

        fun findAllUserRoles(): List<UserRole> {
            return userRoleList
        }

        fun findUsersByRole(role: String): List<UserRole>? {
            return userRoleList.filter { it.role.name.name == role }
        }

        fun addUserRole(userRole: UserRole) {
            userRoleList.add(userRole)
        }

        fun updateUserRole(userRole: UserRole) {
            val index = userRoleList.indexOfFirst { it.user.id == userRole.user.id }
            if (index != -1) {
                userRoleList[index] = userRole
            }
        }

        fun deleteUserRole(userRole: UserRole) {
            userRoleList.remove(userRole)
        }
    }
}