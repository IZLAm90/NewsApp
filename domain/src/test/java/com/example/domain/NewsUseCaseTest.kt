package com.example.domain

import com.app.data.remote.Constants
import com.app.data.remote.UserApi
import com.example.data.model.NewAppData
import com.example.data.model.NewData
import com.example.data.model.Source
import com.example.data.repository.NewsRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsUseCaseTest {
    lateinit var api: UserApi
    lateinit var appRepository: NewsRepo
    lateinit var appUseCase: NewsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        api = mock(UserApi::class.java)
        appRepository = mock(NewsRepo::class.java)
        appUseCase = NewsUseCase(appRepository)
    }


    @Test
    fun `getNews success with data`() = runBlockingTest {
        val fakeData = getFakeData()
        val fakeResponse = createFakeSuccessResponse(fakeData)

        `when`(
            appRepository.getHeadLines(
                com.app.data.remote.Constants.PrefKeys.APP_KEY,
                "n"
            )
        ).thenReturn(flow { emit(fakeResponse) } )
        val result = mutableListOf<NewAppData?>()

         appUseCase.getHeadLines(
            com.app.data.remote.Constants.PrefKeys.APP_KEY,
            "n"
        ).collect(){result.add(it)}

        // Verify that the emitted data matches the fake data
        assert(result.size == 1)
        assert(result[0] == fakeData)
    }

    @Test
    fun `getNews failed with serverError`() = runBlockingTest {
        val fakeResponse =  Response.error<NewAppData>(500, "{\"message\":\"Internal Server Error\" }".toResponseBody())

        `when`(
            appRepository.getHeadLines(
                com.app.data.remote.Constants.PrefKeys.APP_KEY,
                "n"
            )
        ).thenReturn(flow { emit(fakeResponse) } )
        val result = mutableListOf<NewAppData?>()

        // Verify that the emitted data matches the fake data

        try{
            appUseCase.getHeadLines(
                com.app.data.remote.Constants.PrefKeys.APP_KEY,
                "n"
            ).single()
        }catch (e:Throwable){
            Assert.assertEquals(Constants.ERROR_API.SERVER_ERROR, e.message)
        }
    }
    fun createFakeSuccessResponse(data: NewAppData): Response<NewAppData> {
        return Response.success(data)

    }
    fun getFakeData(): NewAppData {
        return NewAppData(
            status = "ok",
            totalResults = data().size,
            articles = data()
        )
    }

    fun data(): ArrayList<NewData> {
        val list = ArrayList<NewData>()
        list.add(
            NewData(
                0,
                "islam",
                "okookokokokokokokok",
                "hi this is islam from arab apps",
                "2023-07-29T20:52:10Z",
                source = Source("ada")
            )
        )
        list.add(
            NewData(
                1,
                "islam",
                "okookokokokokokokok",
                "hi this is islam from arab ",
                "2023-07-28T20:52:10Z",
                source = Source("ada")
            )
        )
        list.add(
            NewData(
                2,
                "islam",
                "okookokokokokokokok",
                "hi this is islam from ",
                "2023-07-27T20:52:10Z",
                source = Source("ada")
            )
        )
        return list
    }
}