package edu.mirea.onebeattrue.factorialtest

sealed class State

data object Error : State()
data object Progress : State()
class Result(val factorial: String) : State()