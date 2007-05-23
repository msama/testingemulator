/**
 * 
 */
package ucl.cs.testingEmulator.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.microemu.microedition.ImplFactory;

import ucl.cs.testingEmulator.connection.file.ConcreteFileConnection;
import ucl.cs.testingEmulator.connection.file.FileConnectorDelegate;


/**
 * @author -Michele Sama- aka -RAX-
 * 
 *
 * University College London
 * Dept. of Computer Science
 * Gower Street
 * London WC1E 6BT
 * United Kingdom
 *
 * Email: M.Sama (at) cs.ucl.ac.uk
 *
 * Group:
 * Software Systems Engineering
 *
 */
public class TestingEmulator {

	private static TestingEmulator INSTANCE=null;
	private PropertyChangeSupport _propertyChangeSupport=null;
	
	
	
	
	/**Specify if bluetooth is active*/
	private boolean _JSR82Loaded=true;
	public static final String JSR82="Jsr82";
	
	private boolean _JSR75PimLoaded=false;
	public static final String JSR75PIM="Jsr75pim";
	
	private boolean _JSR75FcLoaded=false;
	public static final String JSR75FC="Jsr75fc";
	protected FileConnectorDelegate _fileConnectorDelegate=new FileConnectorDelegate();
	
	private boolean _JSR179Loaded=false;
	public static final String JSR179="Jsr179";
	
	
	/**
	 * 
	 */
	private TestingEmulator() {
		this._propertyChangeSupport=new PropertyChangeSupport(this);
		this.setJSR179Loaded(true);
		this.setJSR82Loaded(true);
		this.setJSR75FcLoaded(true);
		this.setJSR75PimLoaded(true);
	}

	/**
	 * @return The Singleton instance of TestingEmulator.
	 */
	public static TestingEmulator getInstance()
	{
		if(TestingEmulator.INSTANCE==null)
		{
			TestingEmulator.INSTANCE=new TestingEmulator();
		}
		return TestingEmulator.INSTANCE;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestingJFrame.main(args);
	}

	/**
	 * @param _JSR82Loaded the _JSR82Loaded to set
	 */
	public void setJSR82Loaded(boolean _JSR82Loaded) {
		this._propertyChangeSupport.firePropertyChange(TestingEmulator.JSR82,this._JSR82Loaded,_JSR82Loaded);
		this._JSR82Loaded = _JSR82Loaded;
	}

	/**
	 * @return the _JSR82Loaded
	 */
	public boolean isJSR82Loaded() {
		return _JSR82Loaded;
	}

	/**
	 * @param _JSR75PimLoaded the _JSR75PimLoaded to set
	 */
	public void setJSR75PimLoaded(boolean _JSR75PimLoaded) {
		this._propertyChangeSupport.firePropertyChange(TestingEmulator.JSR75PIM,this._JSR75PimLoaded,_JSR75PimLoaded);
		this._JSR75PimLoaded = _JSR75PimLoaded;
	}

	/**
	 * @return the _JSR75PimLoaded
	 */
	public boolean isJSR75PimLoaded() {
		return _JSR75PimLoaded;
	}

	/**
	 * @param _JSR75FcLoaded the _JSR75FcLoaded to set
	 */
	public void setJSR75FcLoaded(boolean _JSR75FcLoaded) {
		//pre 
		if(this._JSR75FcLoaded==_JSR75FcLoaded)
		{
			return;
		}
		
		
		this._propertyChangeSupport.firePropertyChange(TestingEmulator.JSR75FC,this._JSR75FcLoaded,_JSR75FcLoaded);
		if(_JSR75FcLoaded==true)
		{
			ImplFactory.registerGCF("file", this._fileConnectorDelegate);
		}else
		{
			ImplFactory.unregistedGCF("file", this._fileConnectorDelegate);
		}
		this._JSR75FcLoaded = _JSR75FcLoaded;
		
	}

	/**
	 * @return the _JSR75FcLoaded
	 */
	public boolean isJSR75FcLoaded() {
		return _JSR75FcLoaded;
	}

	/**
	 * @param arg0
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listener.propertyChange(new PropertyChangeEvent(this,TestingEmulator.JSR82,null,this._JSR82Loaded));
		listener.propertyChange(new PropertyChangeEvent(this,TestingEmulator.JSR75PIM,null,this._JSR75PimLoaded));
		listener.propertyChange(new PropertyChangeEvent(this,TestingEmulator.JSR75FC,null,this._JSR75FcLoaded));
		listener.propertyChange(new PropertyChangeEvent(this,TestingEmulator.JSR179,null,this._JSR179Loaded));
		_propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * @param act
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String act, PropertyChangeListener listener) {
		if(act.equals(TestingEmulator.JSR82))
		{
			listener.propertyChange(new PropertyChangeEvent(this,TestingEmulator.JSR82,null,this._JSR82Loaded));
		}else if(act.equals(TestingEmulator.JSR75PIM))
		{
			listener.propertyChange(new PropertyChangeEvent(this,TestingEmulator.JSR75PIM,null,this._JSR75PimLoaded));
		}else if(act.equals(TestingEmulator.JSR75FC))
		{
			listener.propertyChange(new PropertyChangeEvent(this,TestingEmulator.JSR75FC,null,this._JSR75FcLoaded));
		}
		else if(act.equals(TestingEmulator.JSR179))
		{
			listener.propertyChange(new PropertyChangeEvent(this,TestingEmulator.JSR179,null,this._JSR179Loaded));
		}
		_propertyChangeSupport.addPropertyChangeListener(act, listener);
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		_propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * @param act
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String act, PropertyChangeListener listener) {
		_propertyChangeSupport.removePropertyChangeListener(act, listener);
	}

	/**
	 * @param _JSR179Loaded the _JSR179Loaded to set
	 */
	public void setJSR179Loaded(boolean _JSR179Loaded) {
		this._propertyChangeSupport.firePropertyChange(TestingEmulator.JSR179,this._JSR179Loaded,_JSR179Loaded);
		this._JSR179Loaded = _JSR179Loaded;
	}

	/**
	 * @return the _JSR179Loaded
	 */
	public boolean isJSR179Loaded() {
		return _JSR179Loaded;
	}

}
