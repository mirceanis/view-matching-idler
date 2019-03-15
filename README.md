# view-matching-idler
An `IdlingResource` and a `TestRule` that keep espresso busy while 
at least one view matches a `Matcher<View>`

[![](https://jitpack.io/v/mirceanis/view-matching-idler.svg)](https://jitpack.io/#mirceanis/view-matching-idler)

Sometimes it's too hard or complicated to set up `IdlingResource`s to let espresso know
when your app is busy.
In a lot of those cases there may also be a visual indicator on screen.
You can use this library to mark the presence of the indicator as a hint that espresso should wait. 

That indicator can be whatever view or hierarchy you want as long as you can describe it
as a `Matcher<View>` 

## Import

In your root `build.gradle` file:
```groovy
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}

```

In your app `build.gradle` file:
```groovy
dependencies {
    //...
    androidTestImplementation "com.github.mirceanis:view-matching-idler:0.1"
}
```

## Usage

It can be used as a `TestRule` or, if you want more granular control over it, as an `IdlingResource` 

### Using a `TestRule`

Here's an example that makes espresso wait as long as the "loading" text is visible:

```kotlin
    //simply declare it in your test class:
    @get:Rule
    val viewMatcherIdlingRule = ViewMatcherIdlingRule(
        //be sure to customize the blocking view matcher based on your case
        allOf(withText("loading"), isDisplayed())
    )
    
    @Test
    fun myFancyTest() {
        //normal espresso testing, but espresso will wait while the view matcher condition is met
    }
```

### Using an `IdlingResource`

Here's an example that makes espresso wait as long as the "loading" text is visible:

```kotlin

    @Test
    fun someTestMethod() {
    
        //... some testing

        //This will keep espresso busy while at least one view matches this matcher
        val idler = ViewMatcherIdler(allOf(withText("loading"), isDisplayed()))
        IdlingRegistry.getInstance().register(idler)

        //... some more testing, but now espresso waits
        //... during the times the matcher from above matches a view

        IdlingRegistry.getInstance().unregister(idler)
    }
```

Check out the sample app included in this repo for a practical example.

## Contributing

The best way to contribute to this is to use it and find issues.
PRs welcome.

## Terms of use

You agree to be a nice person.
