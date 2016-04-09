using UnityEngine;
using System.Collections;

public class googleSearch : MonoBehaviour {

    public string url;

    public Renderer thumbnail;
    public WWW www;
    private bool on = false;

    void Start()
    {
        //url = "http://static.stereogum.com/uploads/2013/12/Toto-Africa.jpg";
        
        
    }

    void Update()
    {
        /*if(url != null && !on)
        {
            DownloadImage(url);
        }*/
        
    }

    public void DownloadImage(string url)
    {

        StartCoroutine(coDownloadImage(url));
        on = true;


    }

    IEnumerator coDownloadImage(string imageUrl)
    {
        
        www = new WWW(imageUrl);
        Debug.Log(www.error);
        

        yield return www;
        


        thumbnail.material.mainTexture = new Texture2D(www.texture.width, www.texture.height, TextureFormat.DXT1, false);
        Debug.Log("titi");
        www.LoadImageIntoTexture(thumbnail.material.mainTexture as Texture2D);
        www.Dispose();
        www = null;

    }

    // Update is called once per frame
    

}
