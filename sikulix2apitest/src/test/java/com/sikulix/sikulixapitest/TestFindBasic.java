package com.sikulix.sikulixapitest;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.sikuli.script.App;
import org.sikuli.script.Image;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.sikuli.script.RunTime;
import org.sikuli.util.Debug;
import org.sikuli.util.Settings;

public class TestFindBasic {

  private static final String logname = "TestFindBasic";
  private static final RunTime sxRuntime = RunTime.get();
  private static Region window = null;
  private static String theClass = "";
  private static String images = "";

  private static Image anImage = null;
  private static String anImageFile = "";

//<editor-fold defaultstate="collapsed" desc="logging">
  private static final Logger logger = LogManager.getLogger("SX." + logname);

  private static void debug(String message, Object... args) {
    message = String.format(message, args).replaceFirst("\\n", "\n          ");
    logger.debug(message);
  }

  private static void trace(String message, Object... args) {
    message = String.format(message, args).replaceFirst("\\n", "\n          ");
    logger.trace(message, args);
  }

  private static void error(String message, Object... args) {
    message = String.format(message, args).replaceFirst("\\n", "\n          ");
    logger.error(message);
  }

  private static void info(String message, Object... args) {
    message = String.format(message, args).replaceFirst("\\n", "\n          ");
    logger.info(message);
  }

  private void entry(Object... args) {
    if (args.length == 0) {
      start("");
    } else {
      start(args[0].toString());
    }
    logger.entry(args);
  }

  private void exit(Object... args) {
    end();
    if (args.length == 0) {
      logger.exit(0);
    } else {
      logger.exit(String.format("msec:%d return:%d", duration, args[0]));
    }
  }

  private static void print(String message, Object... args) {
    System.out.println(String.format(message, args));
  }

  private static void terminate(int retval, String message, Object... args) {
    logger.fatal(String.format(" *** terminating: " + message, args));
    System.exit(retval);
  }

  private void start(String desc) {
    startTime = new Date().getTime();
    testdesc = desc;
  }

  private void end() {
    duration = new Date().getTime() - startTime;
  }
//</editor-fold>

  private static long startTime;
  private long duration = 0;
  private String testdesc = "";

  public TestFindBasic() {
    theClass = this.getClass().toString().substring(6);
    images = theClass + "/images.sikuli";
    trace("TestRun: %s", theClass);
  }

  @BeforeClass
  public static void setUpClass() {
    Settings.InfoLogs = false;
    Settings.ActionLogs = false;
    Settings.DebugLogs = false;
    trace("setUpClass:");
    App.openLink("http://www.sikulix.com/uploads/1/4/2/8/14281286/1389697664.png");
    App.pause(3);
    window = App.focusedWindow();
    trace("searching in region %s", window.toJSON());
    Debug.on(3);
  }

  @AfterClass
  public static void tearDownClass() {
    trace("tearDownClass:");
    ImagePath.remove(images);
    Debug.off();
    App.closeWindow();
  }

  @Before
  public void setUp() {
    debug("------- setUp --------------------------------------");
    ImagePath.setBundlePath(images);
  }

  @After
  public void tearDown() {
    debug("----- tearDown msec:%d --- %s *****", duration, testdesc);
  }

  @Test
  public void testFirstFind() {
    debug("------- testFirstFind");
    start("first find - image loaded and cached");
    Match found = window.exists("logo");
    end();
    anImageFile = window.save("testFirstFind");
    debug("aTest: found: %s", found.toJSON());
    assertTrue(found != null);
  }

  @Test
  public void testSecondFind() {
    debug("------- testSecondFind");
    start("second find - cached image reused - no check last seen");
    Settings.CheckLastSeen = false;
    Match found = window.exists("logo");
    end();
    debug("aTest: found: %s", found.toJSON());
    assertTrue(found != null);
  }
  
  @Test
  public void testSecondFindLastSeen() {
    debug("------- testSecondFindLastSeen");
    start("second find - cached image reused - check last seen");
    Settings.CheckLastSeen = true;
    Match found = window.exists("logo");
    end();
    debug("aTest: found: %s", found.toJSON());
    assertTrue(found != null);
  }
  
  @Test
  public void testFindInImageLoaded() {
    debug("------- testFindInImageLoaded");
    start("find in an image loaded from filesystem");
    anImage = Image.get(anImageFile);
    Match found = anImage.find("logo");
    end();
    debug("aTest: found: %s", found.toJSON());
    assertTrue(found != null);
  }
  
  @Test
  public void testFindInImageInMemory() {
    debug("------- testFindInImageInMemory");
    start("find in an image in cache");
    Match found = anImage.find("logo");
    end();
    debug("aTest: found: %s", found.toJSON());
    assertTrue(found != null);
  }

  @Test
  public void testCompare2ImagesInMemory() {
    debug("------- testCompare2ImagesInMemory");
    start("compare 2 cached images ");
    Match found = anImage.find(anImage);
    end();
    debug("aTest: found: %s", found.toJSON());
    assertTrue(found != null);
  }

  @Test
  public void testCompare2ImagesLoaded() {
    debug("------- testCompare2ImagesLoaded");
    start("");
    String anImageFile1 = window.save("image1");
    String anImageFile2 = window.save("image2");
    end();
    debug("capture and store 2 images: %d msec", duration);    
    start("compare 2 images loaded from filesystem");
    Match found = Image.get(anImageFile1).find(anImageFile2);
    end();
    debug("aTest: found: %s", found.toJSON());
    assertTrue(found != null);
  }
}
