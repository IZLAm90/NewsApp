package com.example.newsapp.ui.home

import app.cash.turbine.test
import com.app.data.remote.Constants
import com.app.data.remote.Constants.PrefKeys.APP_KEY
import com.app.data.remote.NetWorkState
import com.example.data.model.NewAppData
import com.example.data.model.NewData
import com.example.data.model.Source
import com.example.data.repository.NewsRepo
import com.example.domain.NewsUseCase
import kotlinx.coroutines.flow.flow
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
        val fakeData = NewAppData("ok",createSampleNewsData().size,createSampleNewsData())
        Mockito.`when`(
            useCase.getHeadLines( APP_KEY
                , "q")
        ).thenReturn(flowOf(fakeData))
        viewModel.newsFlow.test {
            viewModel.getNewsHeadLines(1,15,"en",APP_KEY,"q")
            assertEquals(awaitItem(),NetWorkState.Loading)
            assertEquals(awaitItem(),NetWorkState.Success(fakeData))
            assertEquals(awaitItem(),NetWorkState.StopLoading)
            expectNoEvents()
        }

    }

    @org.junit.Test
    fun `getNews with bad request`() = coroutineRule.runBlockingTest {
        val error= Throwable(Constants.ERROR_API.BAD_REQUEST)
        Mockito.`when`(
            useCase.getHeadLines(
                APP_KEY, "q"
            )
        ).thenReturn(flow { throw error })

        viewModel.newsFlow.test {
            viewModel.getNewsHeadLines(1,15,"en",APP_KEY,"q")
            assertEquals(awaitItem(), NetWorkState.Loading)
            assertEquals(awaitItem(), NetWorkState.Success(null))
            val result = awaitItem() as NetWorkState.Error
            assertEquals(error, result.th)
            assertEquals(awaitItem(), NetWorkState.StopLoading)
            expectNoEvents()
        }
    }
    fun createSampleNewsData(): ArrayList<NewData> {
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

        return arrayListOf(newData1, newData2)
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