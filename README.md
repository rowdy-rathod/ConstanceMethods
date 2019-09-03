# ConstanceMethods
We can access all types of Constance methods. 
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.rowdy-rathod:ConstanceMethods:0.1.1'
	}




Try this => 

    //Save boolean value in preferences true/false
    ConstanceMethods.saveBooleanPreferences(this, Constance.KEY_IS_LOGGED_IN_USER, true);

    //get boolean value from preferences
    Boolean isUserLogin = (Boolean) ConstanceMethods.getPreferences(this, Constance.KEY_IS_LOGGED_IN_USER, Constance.PREF_TYPE_BOOLEAN);
    
    //Save string in preferences it may any string.
    ConstanceMethods.saveStringPreferences(this, Constance.F_NAME, "Rowdy Rathod");

    // get String value from preferences 
    String firstName = (String) ConstanceMethods.getPreferences(this, Constance.F_NAME, Constance.PREF_TYPE_STRING);

    //Save integer value in preferences it may any int value.
    ConstanceMethods.saveStringPreferences(this, Constance.F_NAME, "Rowdy Rathod");

    // get String value from preferences 
    int id = (int) ConstanceMethods.getPreferences(this, Constance.ID, Constance.PREF_TYPE_INT);
    
    
    // We can set Animation any Android views like button, textview, imageView, layout
    
    
    imageView.setAnimation(ConstanceMethods.getAnimationBottomToTop(this));
    imageView.setAnimation(ConstanceMethods.getAnimationTopToBottom(this));
    imageView.setAnimation(ConstanceMethods.getAnimationRightToLeft(this));
    imageView.setAnimation(ConstanceMethods.getAnimationLeftToRight(this));
