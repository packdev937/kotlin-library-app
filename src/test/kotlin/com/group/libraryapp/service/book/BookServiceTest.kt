package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.IllegalArgumentException

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookRepository: BookRepository,
    private val bookService: BookService,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @BeforeEach
    fun setup() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
        userLoanHistoryRepository.deleteAll()
    }

    @Test
    fun 책을_저장한다() {
        // given
        val request = BookRequest("클린 코드")

        // when
        bookService.saveBook(request)

        // then
        val books = bookRepository.findAll()
        Assertions.assertThat(books).hasSize(1)
        Assertions.assertThat(books[0].name).isEqualTo("클린 코드")
    }

    @Test
    fun 책을_대출한다() {
        // given
        val user = userRepository.save(User("백건우", 24))
        val book = bookRepository.save(Book("클린 코드"))
        val request = BookLoanRequest(user.name, book.name)

        // when
        bookService.loanBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        Assertions.assertThat(results).hasSize(1)
        Assertions.assertThat(results[0].bookName).isEqualTo("클린 코드")
        Assertions.assertThat(results[0].user.id).isEqualTo(user.id)
        Assertions.assertThat(results[0].isReturn).isFalse
    }

    @Test
    fun 대출이_있다면_신규_대출이_실패한다() {
        // given
        val user = userRepository.save(User("백건우", 24))
        val book = bookRepository.save(Book("클린 코드"))
        val request = BookLoanRequest(user.name, book.name)
        bookService.loanBook(request)

        // when
        assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }
    }

    @Test
    fun 대출을_반납한다() {
        // given
        val user = userRepository.save(User("백건우", 24))
        val book = bookRepository.save(Book("클린 코드"))
        val request = BookLoanRequest(user.name, book.name)
        bookService.loanBook(request)

        // when
        bookService.returnBook(BookReturnRequest("백건우", "클린 코드"))

        // then
        var results = userLoanHistoryRepository.findAll()
        Assertions.assertThat(results[0].isReturn).isTrue()
    }
}