/**
 *
 */
package ucl.cs.testingEmulator.connection.file;

import javax.microedition.io.file.FileSystemRegistry;

/**
 * @author -Michele Sama- aka -RAX-
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
public class EmulatedFileSystem {
	
	private int _access=-1;
	private String _realPath=null;
	private String _logicalPath=null;
	
	/**
	 * @param _realPath
	 * @param _logicalPath
	 */
	public EmulatedFileSystem(String _realPath, String _logicalPath, int _access) {
		super();
		this._realPath = _realPath;
		this._logicalPath = _logicalPath;
		this._access=_access;
	}
	/**
	 * @param _realPath the _realPath to set
	 */
	public void setRealPath(String _realPath) {
		this._realPath = _realPath;
		if(this._realPath.endsWith(""+FileSystemRegistry.FILE_SEPARATOR)&&this._realPath.length()>1)
		{
			this._realPath=this._realPath.substring(0, this._realPath.length()-1);
		}
	}
	/**
	 * @return the _realPath
	 */
	public String getRealPath() {
		return _realPath;
	}
	/**
	 * @param _logicalPath the _logicalPath to set
	 */
	public void setLogicalPath(String _logicalPath) {
		this._logicalPath = _logicalPath;
		if(this._logicalPath.endsWith(""+FileSystemRegistry.FILE_SEPARATOR)&&this._logicalPath.length()>1)
		{
			this._logicalPath=this._logicalPath.substring(0, this._logicalPath.length()-1);
		}
	}
	/**
	 * @return the _logicalPath
	 */
	public String getLogicalPath() {
		return _logicalPath;
	}
	/**
	 * @param _access the _access to set
	 */
	public void setAccess(int _access) {
		this._access = _access;
	}
	/**
	 * @return the _access
	 */
	public int getAccess() {
		return _access;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if( (obj instanceof EmulatedFileSystem) == false ){ return false; }
		EmulatedFileSystem sys=(EmulatedFileSystem)obj;
		return this.getLogicalPath().equals(sys.getLogicalPath());
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Emulated path: "+this.getLogicalPath()+" - Host path: "+this.getRealPath();
	}
	

}
