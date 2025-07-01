package com.example.aiproject

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CarsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun carList_displaysCorrectItems() {
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Toyota").fetchSemanticsNodes().any()
        }

        composeTestRule.onNodeWithTag("carsList").performScrollToNode(hasText("Toyota"))
        composeTestRule.onAllNodesWithText("Toyota")[0].assertIsDisplayed()

        composeTestRule.onNodeWithTag("carsList").performScrollToNode(hasText("Camry"))
        composeTestRule.onAllNodesWithText("Camry")[0].assertIsDisplayed()
    }
}