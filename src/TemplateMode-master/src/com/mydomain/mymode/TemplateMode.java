package com.mydomain.mymode;

import java.io.File;
import processing.app.*;
import processing.app.ui.*;
import processing.mode.java.*;
import processing.mode.java.runner.Runner;

public class TemplateMode extends JavaMode {
	
    public TemplateMode(Base base, File folder) {
        super(base, folder);
    }

    /**
     * Return the pretty/printable/menu name for this mode. This is separate
     * from the single word name of the folder that contains this mode. It could
     * even have spaces, though that might result in sheer madness or total
     * mayhem.
     */
    @Override
    public String getTitle() {
        return "Template";
    }
    
    // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
   
    // Create a new editor associated with this mode.
//    @Override
//    public Editor createEditor(Base base, String path, EditorState state) {
//   		return null;
//    }

    // Returns the default extension for this editor setup.
//    @Override
//    public String getDefaultExtension() {
//        return null;
//    }

    // Returns a String[] array of proper extensions.    
//    @Override
//    public String[] getExtensions() {
//        return null;
//    }

    // Get array of file/directory names that needn't be copied during "Save As".    
//    @Override
//    public String[] getIgnorable() {
//        return null;
//    }
    
    /**
     * Retrieve the ClassLoader for JavaMode. This is used by Compiler to load
     * ECJ classes. Thanks to Ben Fry.
     * @return the class loader from java mode
     */
//    @Override
//    public ClassLoader getClassLoader() {
//        for (Mode m : base.getModeList()) {
//            if (m.getClass() == JavaMode.class) {
//                JavaMode jMode = (JavaMode) m;
//                return jMode.getClassLoader();
//            }
//        }
//        return null;  // badness
//    }
    
    /** Handles the standard Java "Run" or "Present" */
    @Override
    public Runner handleLaunch(Sketch sketch, RunnerListener listener,
    		final boolean present) throws SketchException {

		String sketchName = sketch.getName();
		System.out.println("handleLaunch: " + sketchName);
		
		Editor editor = base.getActiveEditor();
		String s = "";
		for (int i = 0; i < editor.getLineCount(); i++){
			s += editor.getLineText(i);
		}
		System.out.println(s);
    	
      JavaBuild build = new JavaBuild(sketch);
      String appletClassName = build.build(true);
      
      if (appletClassName != null) {
        final Runner runtime = new Runner(build, listener);
        
        new Thread(new Runnable() {
          public void run() {   // these block until finished
            if (present) runtime.present(null);
            else runtime.launch(null);
          }
        }).start();
        
        return runtime;
      }
      return null;
    }    
  
}
