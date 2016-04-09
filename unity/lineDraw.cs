using UnityEngine;
using System.Collections;

public class lineDraw : MonoBehaviour {

    public LineRenderer linerend;
    public GameObject[] go;
    
    // Use this for initialization
    void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
        float segmentWidth = 0.1f;
        go[0].GetComponent<LineRenderer>().SetVertexCount(70);



        for (int i = 0; i < 70; i++)
        {
            float x = segmentWidth * i;
            float y = Mathf.Sin(x * Time.time);
            go[0].GetComponent<LineRenderer>().SetPosition(i, new Vector3(x, y, 0));

        }
        

    }
}
