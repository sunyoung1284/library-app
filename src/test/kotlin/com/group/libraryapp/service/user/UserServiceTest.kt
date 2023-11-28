package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.repository.user.loanhistory.UserLoanHistoryQuerydslRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName


@SpringBootTest
class UserServiceTest @Autowired constructor(
     private val userRepository: UserRepository,
     private val userService: UserService,
     private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun clean(){
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("유저 저장 정상 동작")
    fun saveUserTest(){
        val request = UserCreateRequest("임선영", null)

        userService.saveUser(request)

        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("임선영")
        assertThat(results[0].age).isNull()

    }
    @Test
    fun getUsersTest(){
        userRepository.saveAll(listOf(
            User("A",20),
            User("B",null),
        ))

        var results = userService.getUsers()
        assertThat(results).hasSize(2)
        assertThat(results).extracting("name").containsExactlyInAnyOrder("A","B") // ["A","B"]
        assertThat(results).extracting("age").containsExactlyInAnyOrder(20,null) // ["A","B"]
    }

    @Test
    fun updateUserNameTest(){
        val savedUser = userRepository.save(User("A",null))
        val request = UserUpdateRequest(savedUser.id!!, "B")

        userService.updateUserName(request)

        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo("B")
    }

    @Test
    fun deleteUserTest(){
        userRepository.save(User("A",null))
        userService.deleteUser("A")
        assertThat(userRepository.findAll()).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 없는 유저도 응답에 포함된다")
    fun getUserLoanHistoriesTest1(){
        userRepository.save(User("선영",null))
        val results = userService.getUserLoanHistories()

        assertThat(results).hasSize(1)

        assertThat(results[0].name).isEqualTo("선영")
        assertThat(results[0].books).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 많은 유저의 응답이 정상 동작한다")
    fun getUserLoanHistoriesTest2(){
        val savedUser = userRepository.save(User("선영",null))
        userLoanHistoryRepository.saveAll(listOf(
            UserLoanHistory.fixture(savedUser, "책1", UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUser, "책2", UserLoanStatus.LOANED),
            UserLoanHistory.fixture(savedUser, "책3", UserLoanStatus.RETURNED),
        ))

        val results = userService.getUserLoanHistories()

        assertThat(results).hasSize(1)

        assertThat(results[0].name).isEqualTo("선영")
        assertThat(results[0].books).hasSize(3)
        assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("책1","책2","책3")
        assertThat(results[0].books).extracting("isReturn")
            .containsExactlyInAnyOrder(false,false,true)
    }




}