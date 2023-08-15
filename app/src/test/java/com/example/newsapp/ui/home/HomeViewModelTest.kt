package com.example.newsapp.ui.home

import com.app.data.remote.NetWorkState
import com.example.data.model.NewData
import com.example.data.model.Source
import com.example.data.repository.NewsRepo
import com.example.domain.NewsUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class HomeViewModelTest {
    lateinit var useCase: NewsUseCase

    lateinit var repo: NewsRepo

    lateinit var viewModel: HomeViewModel

    @get:Rule
    val coroutineRule = CoroutineTestRule()
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repo = Mockito.mock(NewsRepo::class.java)
        useCase = Mockito.mock(NewsUseCase::class.java)
        viewModel = HomeViewModel(useCase)
    }
    @org.junit.Test
    fun `getNews with valid response`() = coroutineRule.runBlockingTest {
        val fakeData = arrayListOf(createSampleNewsData())
//        Mockito.`when`(
//            useCase.getHeadLines(apiKey, q)
//        ).thenReturn(flowOf(fakeData))


    }
    fun createSampleNewsData(): List<NewData> {
        val source = Source(name = "Sample Source")
        val newData1 = NewData(
            id = 1,
            author = "Author 1",
            content = "Content 1",
            description = "Description 1",
            publishedAt = "2023-08-15T12:34:56Z",
            source = source,
            title = "Title 1",
            url = "https://sample-url.com/1",
            urlToImage = "https://sample-image-url.com/1"
        )
        val newData2 = NewData(
            id = 2,
            author = "Author 2",
            content = "Content 2",
            description = "Description 2",
            publishedAt = "2023-08-16T10:20:30Z",
            source = source,
            title = "Title 2",
            url = "https://sample-url.com/2",
            urlToImage = "https://sample-image-url.com/2"
        )
        // Add more sample NewData objects as needed

        return listOf(newData1, newData2)
    }

//    @Test
//    fun `test fetching news headlines`() = testScope.runBlockingTest {
//        // Given
//        val apiKey = "your-api-key"
//        val q = "your-query"
//        val sampleNewsData: List<NewData> = ... // Create sample data for testing
//
//        val flow: Flow<NetWorkState> = flow {
//            emit(NetWorkState.Loading)
//            emit(NetWorkState.Success(sampleNewsData))
//        }
//
//        whenever(mockUseCase.getHeadLines(apiKey, q)).thenReturn(flow)
//
//        val observer: Observer<NetWorkState> = mock()
//        viewModel.newsFlow.observeForever(observer)
//
//        // When
//        viewModel.getNewsHeadLines(1, 10, "en", apiKey, q)
//
//        // Then
//        verify(observer).onChanged(NetWorkState.Loading)
//        verify(observer).onChanged(NetWorkState.Success(sampleNewsData))
//    }
}