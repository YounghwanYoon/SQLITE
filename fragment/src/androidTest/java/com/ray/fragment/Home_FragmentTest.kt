package com.ray.fragment

import android.app.Instrumentation
import android.view.View
import android.widget.RelativeLayout
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.ray.fragment.view.Home_Fragment
import com.ray.fragment.view.TestActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Assert.*
import org.junit.Test


class Home_FragmentTest {

    //Create rule to launch Activity
    @get:Rule
    public var mActivityTestRule:ActivityTestRule<TestActivity>? = ActivityTestRule<TestActivity>(TestActivity::class.java)
    public var mActivity:TestActivity? = null


    @Before
    fun setUp() {
        //instantiate activity
        mActivity = mActivityTestRule!!.activity

    }

    @Test
    fun testLauncher(){
        //Test whether a fragment is launched or not

        //Locally instantiate container of test layout
        var rlContainer:RelativeLayout = mActivity!!.findViewById(R.id.test_container)
        assertNotNull(rlContainer)

        //Create testing fragment object
        val homeFragment = Home_Fragment()
        //Creating fragment manager and commit the tranaction of fragment
        var fragmentManager = mActivity!!.supportFragmentManager
        fragmentManager.beginTransaction().add(rlContainer.id, homeFragment).commitAllowingStateLoss()

        InstrumentationRegistry.getInstrumentation().waitForIdleSync()

        //is my view visible
        var view: View? = homeFragment.view?.findViewById(R.id.home_text)
        assertNotNull(view)

    }
    //After test is completed, this method will run and better to nullify instances created
    @After
    fun tearDown() {
        mActivity = null
        mActivityTestRule = null
    }
}