package net.bytem0use.client.renderers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

public class LaserRenderer {

    List<LaserRenderer.LaserBody> laserSegments = Lists.<LaserBody>newArrayList();
    private List<LaserRenderer.LaserBody> field_1398 = Lists.<LaserBody>newArrayList();
    int level;

    public List<LaserRenderer.LaserBody> getLaserSegments() {
        return this.level == 0 ? ImmutableList.of() : this.laserSegments;
    }

    public List<LaserBody> getField_1398() {
        return field_1398;
    }

    public void setField_1398(List<LaserBody> field_1398) {
        this.field_1398 = field_1398;
    }

    public static class LaserBody {
        final float[] color;
        private int height;

        public LaserBody(float[] color) {
            this.color = color;
            this.height = 1;
        }

        protected void increaseHeight() {
            this.height++;
        }

        public float[] getColor() {
            return this.color;
        }

        public int getHeight() {
            return this.height;
        }
    }
}
