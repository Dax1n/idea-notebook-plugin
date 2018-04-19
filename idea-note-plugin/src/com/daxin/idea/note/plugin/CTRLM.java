package com.daxin.idea.note.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import java.io.File;


/**
 * Created by liuguangxin on 2018/4/17.
 */
public class CTRLM extends AnAction {


    public static String getProjectIdeaPath(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        return project.getBasePath() + File.separator + ".idea" + File.separator;
    }

    public static String getJavaPackageNameAndClassNameAndMEthodName(AnActionEvent e) {
        //获取鼠标所在的元素
        PsiElement psiElement = e.getData(PlatformDataKeys.PSI_ELEMENT);
        if (psiElement == null) {
            return null;
        }
        //获取到方法名
        String methodName = psiElement.toString().replace("PsiMethod:", "");
        PsiElement psiElementParent = psiElement.getParent();//获取方法的父元素
        PsiFile containingFile = psiElementParent.getContainingFile();//获取到文件，这里是java类
        String javaPackageName = containingFile.getContainingDirectory().getName();
        //获取到类名
        String className = containingFile.getName();
        //Main.java#main
        String endDot = javaPackageName + "." + className.replaceAll("java", "");

        return endDot.substring(0, endDot.length() - 1) + "#" + methodName;

    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        String ideaDir = getProjectIdeaPath(e);
        Application application = ApplicationManager.getApplication();
        NoteComponent component = application.getComponent(NoteComponent.class);
        component.codeNoteBook(getJavaPackageNameAndClassNameAndMEthodName(e), ideaDir, e);

    }


}
