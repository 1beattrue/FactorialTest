package edu.mirea.onebeattrue.factorialtest

sealed class State

data object Error : State() // мы делаем его data, чтобы переопределить toString()
data object Progress : State()
data class Result(val factorial: String) : State()