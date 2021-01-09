package com.example.newsappadmin.model;



public class Post
{
    private String postId;
    private String userId;
    private String postTitle;
    private String postDescription;
    private String postImageUrl;
    private String postImageDescription;
    private String postThumbnailUrl;
    private String postCategory;
    private long timestamp;

    public Post()
    {
    }

    public Post(String postId, String userId, String postTitle, String postDescription, String postImageUrl, String postImageDescription, String postThumbnailUrl, String postCategory, long timestamp)
    {
        this.postId = postId;
        this.userId = userId;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postImageUrl = postImageUrl;
        this.postImageDescription = postImageDescription;
        this.postThumbnailUrl = postThumbnailUrl;
        this.postCategory = postCategory;
        this.timestamp = timestamp;
    } // post closed


    public String getPostCategory()
    {
        return postCategory;
    }

    public void setPostCategory(String postCategory)
    {
        this.postCategory = postCategory;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getPostId()
    {
        return postId;
    }

    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    public String getPostTitle()
    {
        return postTitle;
    }

    public void setPostTitle(String postTitle)
    {
        this.postTitle = postTitle;
    }

    public String getPostDescription()
    {
        return postDescription;
    }

    public void setPostDescription(String postDescription)
    {
        this.postDescription = postDescription;
    }

    public String getPostImageUrl()
    {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl)
    {
        this.postImageUrl = postImageUrl;
    }

    public String getPostImageDescription()
    {
        return postImageDescription;
    }

    public void setPostImageDescription(String postImageDescription)
    {
        this.postImageDescription = postImageDescription;
    }

    public String getPostThumbnailUrl()
    {
        return postThumbnailUrl;
    }

    public void setPostThumbnailUrl(String postThumbnailUrl)
    {
        this.postThumbnailUrl = postThumbnailUrl;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }


    @Override
    public String toString()
    {
        return "Post{" +
                "postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", postDescription='" + postDescription + '\'' +
                ", postImageUrl='" + postImageUrl + '\'' +
                ", postImageDescription='" + postImageDescription + '\'' +
                ", postThumbnailUrl='" + postThumbnailUrl + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
} // Post class closed