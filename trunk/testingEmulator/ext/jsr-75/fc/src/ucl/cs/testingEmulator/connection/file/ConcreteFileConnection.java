/**
 *
 */
package ucl.cs.testingEmulator.connection.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
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
public class ConcreteFileConnection implements FileConnection {

	public final static ConcreteFileConnection defaultInstance=new ConcreteFileConnection();
	
	protected String _virtualName=null;
	protected File _file=null;
	private FileInputStream _inputStream;
	private OutputStream _outputStream;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public long availableSize() {
		// TODO get availableSize
		throw new java.lang.RuntimeException("Method not yet implemented!");
	}

	public void create() throws IOException {
		this._file.createNewFile();
	}

	public long directorySize(boolean includeSubDirs) throws IOException {
		// TODO get availableSize
		throw new java.lang.RuntimeException("Method not yet implemented!");
	}

	public long fileSize() throws IOException {
		return this._file.length();
	}

	public String getURL() {
		return this._virtualName;
	}

	public boolean isOpen() {
		return this._file!=null;
	}

	public Enumeration list(String filter, boolean includeHidden) throws IOException {
		
		// TODO get availableSize
		throw new java.lang.RuntimeException("Method not yet implemented!");
		/*
		String[] child=super.list();
		Vector<String> files=new Vector<String>();
		for(String s :child)
		{
			if(s.e)
		}*/
	}

	public DataInputStream openDataInputStream() throws IOException {
		return new DataInputStream(new FileInputStream(this._file));
		//return null;
	}

	public DataOutputStream openDataOutputStream() throws IOException {
		return new DataOutputStream(new FileOutputStream(this._file));
		//return null;
	}

	public InputStream openInputStream() throws IOException {
		if(this._inputStream==null){
			this._inputStream = new FileInputStream(this._file);
		}
		return this._inputStream;
	}

	public OutputStream openOutputStream() throws IOException {
		if(this._outputStream==null){
			this._outputStream = new FileOutputStream(this._file);
		}
		return this._outputStream;
	}

	public OutputStream openOutputStream(long byteOffset) throws IOException {
		// TODO openOutputStream(long byteOffset)
		throw new java.lang.RuntimeException("Method not yet implemented!");
	}

	public void rename(String newName) throws IOException {
		File newFile=FileSystemRegistry.getInstance().openFile(newName);
		if(newFile==null)
		{
			return;
		}
		this._file.renameTo(newFile);
		
	}

	public void setFileConnection(String fileName) throws IOException {
		this.close();
		this.setParameters(fileName, Connector.READ_WRITE, false);
	}

	public void setHidden(boolean hidden) throws IOException {
		// TODO Auto-generated method stub
		throw new java.lang.RuntimeException("Method not yet implemented!");
	}

	public void setReadable(boolean readable) throws IOException {
		// TODO Auto-generated method stub
		throw new java.lang.RuntimeException("Method not yet implemented!");
	}

	public void setWritable(boolean writable) throws IOException {
		// TODO Auto-generated method stub
		throw new java.lang.RuntimeException("Method not yet implemented!");
	}

	public long totalSize() {
		// TODO Auto-generated method stub
		throw new java.lang.RuntimeException("Method not yet implemented!");
	}

	public void truncate(long byteOffset) throws IOException {
		// TODO Auto-generated method stub
		throw new java.lang.RuntimeException("Method not yet implemented!");
		
	}

	public long usedSize() {
		// TODO Auto-generated method stub
		throw new java.lang.RuntimeException("Method not yet implemented!");
	}

	public void close() throws IOException {
		
		this._file=null;
	}

	/**
	 * @return
	 * @see java.io.File#canRead()
	 */
	public boolean canRead() {
		return _file.canRead();
	}

	/**
	 * @return
	 * @see java.io.File#canWrite()
	 */
	public boolean canWrite() {
		return _file.canWrite();
	}

	/**
	 * @return
	 * @see java.io.File#delete()
	 */
	public void delete() {
		_file.delete();
	}

	/**
	 * @return
	 * @see java.io.File#exists()
	 */
	public boolean exists() {
		//pre
		if(this._file==null)
		{
			return false;
		}
		return _file.exists();
	}

	/**
	 * @return
	 * @see java.io.File#getName()
	 */
	public String getName() {
		return this._virtualName.substring(this._virtualName.lastIndexOf(FileSystemRegistry.FILE_SEPARATOR)+1);
	}

	/**
	 * @return
	 * @see java.io.File#getPath()
	 */
	public String getPath() {
		System.out.println("ConcreteFileConnection.getPath original URL: "+this._virtualName);
		if(this._virtualName.startsWith(FileSystemRegistry.LOCAL_URL_PREFIX)==true)
		{
			int k=FileSystemRegistry.LOCAL_URL_PREFIX.length();
			int j=this._virtualName.lastIndexOf(FileSystemRegistry.FILE_SEPARATOR);
				String s=this._virtualName.substring(k,j);
				System.out.println("ConcreteFileConnection.getPath returned URL: "+s);
				return s;
		}else
		{
			System.out.println("ConcreteFileConnection.getPath returned URL: "+this._virtualName.substring(0,this._virtualName.lastIndexOf(FileSystemRegistry.FILE_SEPARATOR)));
			return this._virtualName.substring(0,this._virtualName.lastIndexOf(FileSystemRegistry.FILE_SEPARATOR));
		}
		
	}

	/**
	 * @return
	 * @see java.io.File#isDirectory()
	 */
	public boolean isDirectory() {
		return _file.isDirectory();
	}

	/**
	 * @return
	 * @see java.io.File#isHidden()
	 */
	public boolean isHidden() {
		return _file.isHidden();
	}

	/**
	 * @return
	 * @see java.io.File#list()
	 */
	public Enumeration list() {
		String[] str= _file.list();
		Vector<String> v=new Vector<String>();
		for(String s:str)
		{
			v.addElement(s);
		}
		return v.elements();
	}

	/**
	 * @return
	 * @see java.io.File#lastModified()
	 */
	public long lastModified() {
		return _file.lastModified();
	}

	/**
	 * @return
	 * @see java.io.File#mkdir()
	 */
	public void mkdir() {
		_file.mkdir();
	}

	public void setParameters(String name, int mode, boolean timeouts) {
		if(name.endsWith(""+FileSystemRegistry.FILE_SEPARATOR)&&name.length()>FileSystemRegistry.LOCAL_URL_PREFIX.length()+1)
		{
			name=name.substring(0,name.length()-1);
		}
		try {
			this._file=FileSystemRegistry.getInstance().openFile(name);
			if(this._file!=null)
			{
				this._virtualName=name;
			}
		} catch (IOException e) {
			//e.printStackTrace();
			return;
		}
		//TODO protections and timeouts
	}



}
