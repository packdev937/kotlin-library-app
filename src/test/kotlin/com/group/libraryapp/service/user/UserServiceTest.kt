package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    @BeforeEach
    fun setup() {
        userRepository.deleteAll()
    }

    @Test
    fun 유저를_저장한다() {
        // given
        val request = UserCreateRequest("백건우", 24)

        // when
        userService.saveUser(request)

        // then
        val requests = userRepository.findAll()

        assertThat(requests).hasSize(1)
        assertThat(requests[0].name).isEqualTo("백건우")
    }

    @Test
    fun 유저를_조회한다() {
        // given
        userRepository.saveAll(
            listOf(
                User("A", 20),
                User("B", 21)
            )
        )

        // when
        val users = userService.getUsers()

        // then
        assertThat(users).hasSize(2)
        assertThat(users).extracting("name").containsExactlyInAnyOrder("A", "B")
        assertThat(users).extracting("age").containsExactlyInAnyOrder(20, 21)
    }

    @Test
    fun 유저_이름을_업데이트한다() {
        // given
        val user = userRepository.save(User("A", 20))
        val request = UserUpdateRequest(user.id, "B")

        // when
        userService.updateUserName(request)

        // then
        val updatedUser = userRepository.findAll()[0]
        assertThat(updatedUser.name).isEqualTo("B")
    }

    @Test
    fun 유저를_삭제한다() {
        // given
        val user = userRepository.save(User("A", 20))

        // when
        userService.deleteUser(user.name)

        // then
        val result = userRepository.findAll()
        assertThat(result).isEmpty()
    }
}