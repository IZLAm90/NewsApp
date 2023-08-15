package com.example.domain

import com.app.data.remote.UserApi
import com.example.data.model.NewAppData
import com.example.data.model.NewData
import com.example.data.model.Source
import com.example.data.repository.NewsRepo
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class NewsUseCaseTestForIslam {
    lateinit var appRepository: NewsRepo
    lateinit var appUseCase: NewsUseCase
    lateinit var api: UserApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        api = Mockito.mock(UserApi::class.java)
        appRepository = Mockito.mock(NewsRepo::class.java)
        appUseCase = Mockito.mock(NewsUseCase::class.java)
    }
    @Test
    fun `getHeadLines - success`() = runBlockingTest {
        // Given
        val apiKey = "your-api-key"
        val q = "your-query"

        val responseBody: NewAppData =getFakeData()
        `when`(appRepository.getHeadLines(apiKey, q)).thenReturn(flowOf(Response.success(responseBody)))

        // When
        val result = appUseCase.getHeadLines(apiKey, q)

        // Then
        assertEquals(responseBody, result.single())
    }


    fun getFakeData() : NewAppData{
        val sampleSource = Source(name = "Sample Source")
        val sampleNewData = NewData(
            id = 1,
            author = "Sample Author",
            content = "Sample Content",
            description = "Sample Description",
            publishedAt = "2023-08-15T12:34:56Z",
            source = sampleSource,
            title = "Sample Title",
            url = "https://sample-url.com",
            urlToImage = "https://sample-image-url.com"
        )

        return NewAppData(
            status = "ok",
            totalResults = 1,
            articles = arrayListOf(sampleNewData)
        )

    }
}