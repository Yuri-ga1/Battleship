package com.example.battleship

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var startBtn: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
        startBtn = findViewById(R.id.startButton)
        startBtn.setOnClickListener {
            start()
        }

    }

    private fun start() {
        val fieldSize = 10
        val fleet = listOf(1 to 4, 2 to 3, 3 to 2, 4 to 1)

        val field = Array(fieldSize) { IntArray(fieldSize) }

        for ((shipSize, shipCount) in fleet) {
            repeat(shipCount) {
                placeShip(field, shipSize)
            }
        }

        printField(field)
    }

    private fun placeShip(field: Array<IntArray>, shipSize: Int) {
        val size = field.size
        val random = Random.Default

        var x: Int
        var y: Int
        var direction: Int
        var validPlacement: Boolean

        do {
            x = random.nextInt(size)
            y = random.nextInt(size)
            direction = random.nextInt(4)
            validPlacement = true


            for (i in 0 until shipSize) {
                val newX = x + if (direction == 1) i else if (direction == 3) -i else 0
                val newY = y + if (direction == 0) -i else if (direction == 2) i else 0

                if (newX < 0 || newX >= size || newY < 0 || newY >= size || field[newX][newY] != 0) {
                    validPlacement = false
                    break
                }

                for (dx in -1..1) {
                    for (dy in -1..1) {
                        val neighborX = newX + dx
                        val neighborY = newY + dy
                        if (neighborX in 0 until size && neighborY in 0 until size && field[neighborX][neighborY] != 0) {
                            validPlacement = false
                            break
                        }
                    }
                    if (!validPlacement) break
                }

                if (!validPlacement) break
            }
        } while (!validPlacement)

        for (i in 0 until shipSize) {
            val newX = x + if (direction == 1) i else if (direction == 3) -i else 0
            val newY = y + if (direction == 0) -i else if (direction == 2) i else 0
            field[newX][newY] = 1
        }
    }

    private fun printField(field: Array<IntArray>) {
        var text: String = ""
        for (row in field) {
            for (cell in row) {
                text += "$cell "
            }
            text +='\n'
        }
        textView.setText(text)
    }
}