package de.uni_muenster.wi.md2library.model.contentProvider.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.uni_muenster.wi.md2library.model.type.interfaces.Md2Entity;

/**
 * Created by felix_000 on 07.05.2017.
 */

public interface Md2MultiContentProvider  extends IContentProvider{


 public Collection<Md2Entity> getContents();

public void setCurrentIndex(int i);

 public ArrayList<Md2Entity> getContentsList();


}
