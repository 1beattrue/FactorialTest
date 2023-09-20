package edu.mirea.onebeattrue.factorialtest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class MainViewModel : ViewModel() {

    private val coroutineScope = CoroutineScope(
        Dispatchers.Main + CoroutineName("My coroutine scope")
    )

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun calculate(value: String?) {
        _state.value = Progress
        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }

        coroutineScope.launch {// в coroutine builder также можно передать coroutineContext
            val number = value.toLong()
            val result = withContext(Dispatchers.Default) {
                factorial(number)
            }
            _state.value = Factorial(value = result)
            Log.d("MainViewModel", coroutineContext.toString())
        }
    }

    // представим, что у нас нет доступа к этой функции и мы не можем сделать ее suspend
    private fun factorial(number: Long): String {
        var result = BigInteger.ONE
        for (i in 1..number) {
            result = result.multiply(BigInteger.valueOf(i))
        }
        return result.toString()
    }

//    private suspend fun factorial(number: Long): BigInteger {
//        // первый вариант вынесения coroutine'ы в другой поток
//        // под капотом обычная функция с callback'ом
//        return suspendCoroutine {
//            thread {
//                var result = BigInteger.ONE
//                for (i in 1..number) {
//                    result = result.multiply(BigInteger.valueOf(i))
//                }
//                it.resumeWith(Result.success(result))
//            }
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}