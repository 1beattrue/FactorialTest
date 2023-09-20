package edu.mirea.onebeattrue.factorialtest

sealed class State

data object Error : State()
data object Progress : State()
data class Factorial(val value: String) : State()