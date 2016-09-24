package net.iquesoft.iquephoto.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import net.iquesoft.iquephoto.DataHolder;
import net.iquesoft.iquephoto.core.EditorImageView;
import net.iquesoft.iquephoto.core.EditorView;
import net.iquesoft.iquephoto.R;
import net.iquesoft.iquephoto.common.BaseFragment;
import net.iquesoft.iquephoto.di.components.IEditorActivityComponent;
import net.iquesoft.iquephoto.presenter.MemeFragmentPresenterImpl;
import net.iquesoft.iquephoto.view.IMemeFragmentView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MemeFragment extends BaseFragment implements IMemeFragmentView {

    private EditorImageView editorView;

    @BindView(R.id.topMemeText)
    EditText topMemeText;

    @BindView(R.id.bottomMemeText)
    EditText bottomMemeText;

    @Inject
    MemeFragmentPresenterImpl presenter;

    private Unbinder unbinder;

    public static MemeFragment newInstance() {
        /*Bundle b = new Bundle();
        b.putString("msg", text);
        b.putString("color", color);
        f.setArguments(b);*/
        return new MemeFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IEditorActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meme, container, false);
        v.setAlpha(0.8f);

        editorView = DataHolder.getInstance().getEditorView();

        unbinder = ButterKnife.bind(this, v);

        //Subscription topMemeTextSubscription =
        RxTextView.textChanges(topMemeText)
                .subscribe(value -> {
                    //editorView.setTopMemeText(value.toString());
                });

        //Subscription bottomMemeTextSubscription =
        RxTextView.textChanges(bottomMemeText)
                .subscribe(value -> {
                    //editorView.setBottomMemeText(value.toString());
                });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
