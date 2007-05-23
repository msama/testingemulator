/**
 * 
 */
package ucl.cs.testingEmulator.bluetooth;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.bluetooth.DataElement;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

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
public class SimulatedDevice implements ServiceRecord {

	
	protected Hashtable<Integer, DataElement> _attributes=new Hashtable<Integer, DataElement>();
	
	
	protected RemoteDevice _remoteDevice=null;
	
	SimulatedDevice()
	{
		//ServiceRecordHandle	0x0000  	32-bit unsigned integer
		this._attributes.put(new Integer(0x0000), new DataElement(DataElement.U_INT_4,0));
		
		//ServiceClassIDList	0x0001 	DATSEQ of UUIDs
		DataElement serviceClassIDList=new DataElement(DataElement.DATSEQ);
		serviceClassIDList.addElement(new DataElement(DataElement.UUID,new UUID(0)));
		this._attributes.put(new Integer(0x0001), serviceClassIDList);
		
		//ServiceRecordState	0x0002 	32-bit unsigned integer
		this._attributes.put(new Integer(0x0002), new DataElement(DataElement.U_INT_4,0));
		
		//ServiceID	0x0003 	UUID
		this._attributes.put(new Integer(0x0003), new DataElement(DataElement.UUID,new UUID(0)));
		
		//ProtocolDescriptorList	0x0004 	DATSEQ of DATSEQ of UUID and optional parameters
		DataElement protocolDescriptorList=new DataElement(DataElement.DATSEQ);
		protocolDescriptorList.addElement(new DataElement(DataElement.UUID,new UUID(0)));
		this._attributes.put(new Integer(0x0004), protocolDescriptorList);
		
		//BrowseGroupList	0x0005 	DATSEQ of UUIDs
		DataElement browseGroupList=new DataElement(DataElement.DATSEQ);
		browseGroupList.addElement(new DataElement(DataElement.UUID,new UUID(0)));
		this._attributes.put(new Integer(0x0005), browseGroupList);
		
		//LanguageBasedAttributeIDList	0x0006 	DATSEQ of DATSEQ triples
		DataElement languageBasedAttributeIDList=new DataElement(DataElement.DATSEQ);
		DataElement languageBasedAttributeIDEntry = new DataElement(DataElement.DATSEQ);
		languageBasedAttributeIDEntry.addElement(new DataElement(DataElement.UUID,new UUID(0)));
		languageBasedAttributeIDEntry.addElement(new DataElement(DataElement.UUID,new UUID(1)));
		languageBasedAttributeIDEntry.addElement(new DataElement(DataElement.UUID,new UUID(2)));
		languageBasedAttributeIDList.addElement(languageBasedAttributeIDEntry);
		this._attributes.put(new Integer(0x0006), languageBasedAttributeIDList);
		
		//ServiceInfoTimeToLive	0x0007 	32-bit unsigned integer
		this._attributes.put(new Integer(0x0007), new DataElement(DataElement.U_INT_4,10000));
		
		//ServiceAvailability	0x0008 	8-bit unsigned integer
		this._attributes.put(new Integer(0x0008), new DataElement(DataElement.U_INT_1,255));
		
		//BluetoothProfileDescriptorList	0x0009 	DATSEQ of DATSEQ pairs
		DataElement bluetoothProfileDescriptorList=new DataElement(DataElement.DATSEQ);
		DataElement bluetoothProfileDescriptorEntry = new DataElement(DataElement.DATSEQ);
		bluetoothProfileDescriptorEntry.addElement(new DataElement(DataElement.UUID,new UUID(0)));
		bluetoothProfileDescriptorEntry.addElement(new DataElement(DataElement.UUID,new UUID(1)));
		bluetoothProfileDescriptorList.addElement(bluetoothProfileDescriptorEntry);
		this._attributes.put(new Integer(0x0009), bluetoothProfileDescriptorList);
		
		//DocumentationURL	0x000A 	URL
		this._attributes.put(new Integer(0x000A), new DataElement(DataElement.URL,"www.DocumentationURL.org"));
		
		//ClientExecutableURL	0x000B	URL
		this._attributes.put(new Integer(0x000B), new DataElement(DataElement.URL,"www.ClientExecutableURL.org"));
		
		//IconURL	0x000C	URL
		this._attributes.put(new Integer(0x000C), new DataElement(DataElement.URL,"www.IconURL.org"));
		
		//VersionNumberList	0x0200 	DATSEQ of 16-bit unsigned integers
		DataElement versionNumberList=new DataElement(DataElement.DATSEQ);
		versionNumberList.addElement(new DataElement(DataElement.U_INT_4,0));
		this._attributes.put(new Integer(0x0200), versionNumberList);
		
		//ServiceDatabaseState	0x0201 	32-bit unsigned integer
		this._attributes.put(new Integer(0x0201), new DataElement(DataElement.U_INT_4,0));
	}
	
	
	/* (non-Javadoc)
	 * @see javax.bluetooth.ServiceRecord#getAttributeIDs()
	 */
	public int[] getAttributeIDs() {
		Enumeration<Integer> enumeration = this._attributes.keys();
		Vector<Integer> buffer=new Vector<Integer>();
		while(enumeration.hasMoreElements())
		{
			buffer.addElement(enumeration.nextElement());
		}
		int[] array=new int[buffer.size()];
		for(int i=0;i<buffer.size();i++)
		{
			array[i]=buffer.elementAt(i).intValue();
		}
		return array;
	}

	/* (non-Javadoc)
	 * @see javax.bluetooth.ServiceRecord#getAttributeValue(int)
	 */
	public DataElement getAttributeValue(int attrID) {
		return this._attributes.get(new Integer(attrID));
	}

	/* (non-Javadoc)
	 * @see javax.bluetooth.ServiceRecord#getConnectionURL(int, boolean)
	 */
	public String getConnectionURL(int requiredSecurity, boolean mustBeMaster) {
//		TODO implements getConnectionURL
		throw new java.lang.RuntimeException("Not yet implemented.");
	}

	/* (non-Javadoc)
	 * @see javax.bluetooth.ServiceRecord#getHostDevice()
	 */
	public RemoteDevice getHostDevice() {
		return this._remoteDevice;
	}

	/* (non-Javadoc)
	 * @see javax.bluetooth.ServiceRecord#populateRecord(int[])
	 */
	public boolean populateRecord(int[] attrIDs) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.bluetooth.ServiceRecord#setAttributeValue(int, javax.bluetooth.DataElement)
	 */
	public boolean setAttributeValue(int attrID, DataElement attrValue) {
		if(attrID<0||attrID==0)
		{
			throw new java.lang.IllegalArgumentException("Param \"attrID\" does not represent a 16-bit unsigned integer or is the value of ServiceRecordHandle (0x0000).");
		}
		
		//TODO java.lang.RuntimeException - if this method is called on a ServiceRecord that was created by a call to DiscoveryAgent.searchServices()
		
		this._attributes.put(new Integer(attrID), attrValue);
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.bluetooth.ServiceRecord#setDeviceServiceClasses(int)
	 */
	public void setDeviceServiceClasses(int classes) {
		//TODO implements setDeviceServiceClasses
		throw new java.lang.RuntimeException("Not yet implemented.");
	}

}
