package net.iquesoft.iquephoto.model;

import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.support.annotation.Nullable;

import net.iquesoft.iquephoto.R;

import java.util.Arrays;
import java.util.List;

/**
 * @author Sergey Belenkiy
 *         Filter model class how works with EffectFactory api.
 */
public class Filter {

    private int title;
    private int image;
    private Effect effect;

    private EffectContext effectContext;
    private EffectFactory effectFactory;

    private static Filter instance = new Filter();

    public static Filter getInstance() {
        return instance;
    }

    /**
     * @return list with all editor tools for adapter usage;
     */
    public static List<Filter> getFiltersList() {
        return Arrays.asList(filters);
    }

    /**
     * Array with all filters;
     */
    public static Filter[] filters = {
            new Filter(R.string.filter_normal, R.drawable.image_1),
            new Filter(R.string.filter_gross_process, R.drawable.image_1),
            new Filter(R.string.filter_gross_process, R.drawable.image_1),
    };


    public Filter() {

    }

    public Filter(int title, int image) {
        this.title = title;
        this.image = image;
    }

    public Filter(int title, int image, @Nullable Effect effect) {
        this.title = title;
        this.image = image;
        this.effect = effect;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public EffectContext getEffectContext() {
        return effectContext;
    }

    public void setEffectContext(EffectContext effectContext) {
        this.effectContext = effectContext;
    }

    public EffectFactory getEffectFactory() {
        return effectFactory;
    }

    public void setEffectFactory(EffectFactory effectFactory) {
        this.effectFactory = effectFactory;
    }

    /**
     * @return effect object for "Gross Process" filter;
     */
    private Effect getCrossProcess() {
        return effectFactory.createEffect(EffectFactory.EFFECT_CROSSPROCESS);
    }
}
