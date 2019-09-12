# ConstanceMethods
We can access all the Constant methods. 
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




Examples => 

    //Save boolean value in preferences true/false
    ConstantMethods.saveBooleanPreferences(this, Constance.KEY_IS_LOGGED_IN_USER, true);

    //get boolean value from preferences
    Boolean isUserLogin = (Boolean) ConstantMethods.getPreferences(this, Constance.KEY_IS_LOGGED_IN_USER, Constance.PREF_TYPE_BOOLEAN);
    
    //Save string in preferences it may any string.
    ConstantMethods.saveStringPreferences(this, Constance.F_NAME, "Rowdy Rathod");

    // get String value from preferences 
    String firstName = (String) ConstantMethods.getPreferences(this, Constance.F_NAME, Constance.PREF_TYPE_STRING);

    //Save integer value in preferences it may any int value.
    ConstantMethods.saveStringPreferences(this, Constance.F_NAME, "Rowdy Rathod");

    // get String value from preferences 
    int id = (int) ConstantMethods.getPreferences(this, Constance.ID, Constance.PREF_TYPE_INT);
    
    
    // We can set Animation any Android views like button, textview, imageView, layout
    
    
    imageView.setAnimation(ConstantMethods.getAnimationBottomToTop(this));
    imageView.setAnimation(ConstantMethods.getAnimationTopToBottom(this));
    imageView.setAnimation(ConstantMethods.getAnimationRightToLeft(this));
    imageView.setAnimation(ConstantMethods.getAnimationLeftToRight(this));
