package yh.app.controller.controller.lib;

import yh.app.controller.controller.view.WheelView;

public final class OnItemSelectedRunnable implements Runnable {
    final WheelView loopView;

   public OnItemSelectedRunnable(WheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getCurrentItem());
    }
}
