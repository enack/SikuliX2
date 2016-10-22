/*
 * Copyright (c) 2016 - sikulix.com - MIT license
 */

package com.sikulix.api;

import com.sikulix.core.SX;
import com.sikulix.core.SXLog;
import com.sikulix.core.Element;

import java.awt.*;

public class Match extends Region {

  private static eType vClazz = eType.MATCH;
  private static SXLog log = SX.getLogger("SX." + vClazz.toString());

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  private int index = 0;

  public Match() {
    clazz = vClazz;
    init(0, 0, 0, 0);
  }

  public Match(Rectangle rect) {
    clazz = vClazz;
    init(rect);
  }

  public Match(Element vis) {
    clazz = vClazz;
    init(vis);
    if (vis.isMatch()) {
      setScore(((Match) vis).getScore());
      setOffset(vis.getOffset());
    }
  }

  public Match(Element vis, Double score, Offset off) {
    clazz = vClazz;
    init(vis);
    setScore(score);
    setOffset(off);
  }
}
