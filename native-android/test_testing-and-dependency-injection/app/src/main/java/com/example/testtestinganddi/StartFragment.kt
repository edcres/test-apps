package com.example.testtestinganddi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.testtestinganddi.databinding.FragmentStartBinding

// integration tests vs integrated tests

/**
 *
 * // https://developer.android.com/codelabs/advanced-android-kotlin-training-testing-basics?authuser=2#0
 * // https://developer.android.com/codelabs/advanced-android-kotlin-training-testing-test-doubles?authuser=2#0
 *
 * The important things are in the testing folders, so go look at that code.
 *
 * When you write tests that include testing LiveData, use InstantTaskExecutorRule
 *
 * 'testImplementation' in the gradle file makes the implementation not be downloaded on production (not sure if i said that correctly)
 *
 */

/**
 * Things to consider when testing:
 *
 * Scope, Speed, Fidelity
 *
 * Common way to divide automated tests is into these 3 categories:
 * - 70% Unit tests: (usually a single method in that class)
 * - 20% Integration tests: (These test the interaction of several classes to make sure they behave as expected when used together)
 * - 10% End to end tests (E2e): (They test large portions of the app, simulate real usage closely, and therefore are usually slow.) (By and large, these tests will be instrumented tests)
 */

/** Notes
 *
 * Choose instrumented tests and local tests
 *
 * (folder) androidTest: Contains tests known as instrumented tests
 * (folder) test: Contains tests known as local tests.
 * The difference between local tests and instrumented tests is in the way they are run.
 *
 * - Local tests are run locally on your development machine's JVM and do not require an emulator or physical device. Because of this, they run fast, but their fidelity is lower, meaning they act less like they would in the real world.
 * - Instrumented tests run on real or emulated Android devices, so they reflect what will happen in the real world, but are also much slower.
 *
 *
 * Libraries:
 * - JUnit4: JUnit is a simple framework to write repeatable tests. It is an instance of the xUnit architecture for unit testing frameworks.
 * - Hamcrest: Framework that assists writing software tests. It supports creating customized assertion matchers, allowing match rules to be defined declaratively.
 * - AndroidX Test Library
 * - AndroidX Architecture Components Core Test Library
 *
 *
 * Flaky tests are tests that when run repeatedly on the same code, sometimes pass and sometimes fail (ie. network tests)
 *
 * A test double is a version of a class crafted specifically for testing. It is meant to replace the real version of a class in tests.
 * Fake, Mock, Stub, Dummy, Spy
 */

/**
 * To properly set up AndroidX Test:
 * - Add the AndroidX Test core and ext dependencies
 * - Add the Robolectric Testing library dependency
 * - Annotate the class with the AndroidJunit4 test runner
 * - Write AndroidX Test code
 *
 * // Robolectric provides a simulated Android environment that AndroidX Test uses for local tests.
 * // The AndroidJUnit4 test runner allows for AndroidX Test to run your test differently depending on whether they are instrumented or local tests.
 *
 * // You can use the AndroidX test library to get test versions of components like Applications and Activities.
 *      - allows use of application context
 *
 */

class StartFragment : Fragment() {

    private var binding: FragmentStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(requireParentFragment().requireView())

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            hiltBtn.setOnClickListener {
                navController.navigate(R.id.action_startFragment_to_basicHiltFragment)
            }
            hiltDagger2Btn.setOnClickListener {
                navController.navigate(R.id.action_startFragment_to_basicsHiltAndDagger2Fragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}